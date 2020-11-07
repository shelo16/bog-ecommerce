package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.generator.TimeFunctions;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductFilter;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsGetBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsPurchaseBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsSaveBean;
import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUserProductsPurchaseHistory;
import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import com.tornikeshelia.bogecommerce.model.persistence.repository.DailyReportRepository;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserProductsPurchaseHistoryRepository;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserRepository;
import com.tornikeshelia.bogecommerce.model.persistence.repository.ProductsRepository;
import com.tornikeshelia.bogecommerce.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.bogecommerce.security.service.authentication.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private EcommerceUserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private EcommerceUserProductsPurchaseHistoryRepository ecommerceUserProductsPurchaseHistoryRepository;

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${image-upload-url}")
    private String imgurApi;

    @PersistenceContext
    private EntityManager em;

    /**
     * Save Or Update Secure Method :
     *
     * @param productsSaveBean -> id, name , price, quantity , imageUrl (All @notnull except for id)
     *                         1 -> Checks if user is authenticated and if user exists
     *                         2 -> if ProductBean.getProductId is NULL -> the method will save new Product
     *                         else -> the method will update the existing Product;
     *                         3 -> The method will try to update the dailyReport.getTotalUniqueProductsAdded()
     * @return Long Id of saved product
     **/
    @Override
    @Transactional
    public Long saveProduct(ProductsSaveBean productsSaveBean, HttpServletRequest request) throws IOException {

        String email = null;
        CheckUserAuthResponse authResponse = authenticationService.checkIfUserIsAuthenticated(request);
        if (authResponse.getIsAuthenticated()) {
            email = authResponse.getEmail();
        } else {
            throw new GeneralException(BogError.YOU_MUST_BE_AUTHENTICATED_TO_ADD_A_PRODUCT);
        }

        EcommerceUser user = userRepository.searchByEmail(email);
        if (user == null) {
            throw new GeneralException(BogError.COULDNT_FIND_USER_BY_PROVIDED_EMAIL);
        }

        Products products;
        Date today = new Date();

        if (productsSaveBean.getProductId() == null) {
            // Creates New
            products = new Products();
        } else {
            // Gets Existing For Update
            products = productsRepository.findById(productsSaveBean.getProductId())
                    .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));
        }

        products.setEcommerceUser(user);
        BeanUtils.copyProperties(productsSaveBean, products);
        productsRepository.save(products);

        DailyReport dailyReport = dailyReportRepository.getDailyReportByDateRange(TimeFunctions.getStartOfDay(today), TimeFunctions.getEndOfDay(today));
        if (dailyReport == null) {
            throw new GeneralException(BogError.COULDNT_FIND_DAILY_REPORT_BY_PROVIDED_DATE_RANGE);
        } else {
            dailyReport.setTotalUniqueProductsAdded(dailyReport.getTotalUniqueProductsAdded() + 1);
            dailyReportRepository.save(dailyReport);
        }

        return products.getId();
    }

    /**
     * GetById Method :
     *
     * @param id of product
     *           Searches products using findById Jpa method -> transforms the product to ProductsBean
     * @return productGetBean -> id , name, price, quantity, imageUrl , email of product owner
     **/
    @Override
    public ProductsGetBean getById(Long id) {

        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));
        return ProductsGetBean.transformProductsEntity(products);

    }

    /**
     * GetAll Method :
     * Searches for ALL the products in DB and transforms them to ProductsBean
     *
     * @return productGetBean -> id , name, price, quantity, imageUrl , email of product owner
     **/
    @Override
    public List<ProductsGetBean> getAll() {

        List<Products> productsList = productsRepository.getNewestProducts();
        if (productsList.isEmpty()) {
            throw new GeneralException(BogError.NO_PRODUCTS_FOUND);
        }

        return productsList.stream()
                .map(ProductsGetBean::transformProductsEntity)
                .collect(Collectors.toList());

    }

    /**
     * GetByUserId Method :
     *
     * @param userId Searches for ALL the products in DB by userId and transforms them to ProductsBean
     * @return productGetBean -> id , name, price, quantity, imageUrl , email of product owner
     **/
    @Override
    public List<ProductsGetBean> getByUserId(Long userId, HttpServletRequest request) {
        List<Products> productsList = productsRepository.getByUserId(userId);

        if (productsList.isEmpty()) {
            throw new GeneralException(BogError.NO_PRODUCTS_FOUND);
        }

        return productsList.stream()
                .map(ProductsGetBean::transformProductsEntity)
                .collect(Collectors.toList());
    }

    /**
     * Transactional Method for Purchasing :
     *
     * @param productsPurchaseBean -> productQuantity , userId , productId
     *                             1 -> Finds the necessary information (e.g. products, ownerUser , clientUser , dailyReport ) and checks that values aren't NULL
     *                             2 -> Calculates totalPrice of purchase and checks if clientUser has enough on the balance
     *                             3 -> Checks totalQuantity validity , Subtracts totalQuantity left to product and calculates Commission and PriceAfterCommission
     *                             4 -> Add's priceAfterCommission to owner of the product , subtracts TotalPrice from client's balance
     *                             5 -> Edits info of DailyReport and Creates new PurchaseHistory
     *                             6 -> Saves all the info to DB
     **/
    @Override
    @Transactional
    public void purchaseProduct(ProductsPurchaseBean productsPurchaseBean, HttpServletRequest request) {

        EcommerceUser clientUser = null;
        CheckUserAuthResponse authResponse = authenticationService.checkIfUserIsAuthenticated(request);

        // checking if client user is authenticated
        if (authResponse.getIsAuthenticated()) {
            clientUser = userRepository.searchByEmail(authResponse.getEmail());
            if (clientUser == null) {
                throw new GeneralException(BogError.COULDNT_FIND_USER_BY_PROVIDED_EMAIL);
            }
        } else {
            throw new GeneralException(BogError.YOU_MUST_BE_AUTHENTICATED_TO_ADD_A_PRODUCT);
        }

        Date today = new Date();

        // Setting up necessary objects
        Products products = productsRepository.findById(productsPurchaseBean.getProductId())
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));

        // Checking if Owner userId and client userId's are not null
        if (products.getEcommerceUser() == null) {
            throw new GeneralException(BogError.COULDNT_FIND_USER_FROM_PROVIDED_PRODUCT);
        }

        EcommerceUser ownerUser = products.getEcommerceUser();

        // Checking if client and owner are not the same person
        if (clientUser == ownerUser) {
            throw new GeneralException(BogError.INVALID_REQUEST);
        }

        // Calculating total price
        BigDecimal totalPrice = products.getProductPrice().multiply(new BigDecimal(products.getProductQuantity()));

        // Checking if clientUser has enough on balance
//        if (clientUser.getBalance().compareTo(totalPrice) < 0) {
//            throw new GeneralException(BogError.USER_DOESNT_HAVE_ENOUGH_BALANCE);
//        }

        // Checking if the products stock is valid
        if (products.getProductQuantity() < productsPurchaseBean.getProductQuantity()) {
            throw new GeneralException(BogError.NOT_ENOUGH_PRODUCT_QUANTITY_LEFT_IN_STOCK);
        }

        // Subtracting product quantity
        products.setProductQuantity(products.getProductQuantity() - productsPurchaseBean.getProductQuantity());

        // Calculating commission 10%
        BigDecimal commission = totalPrice.multiply(new BigDecimal(0.1));

        // Calculating price after commission
        BigDecimal priceAfterCommission = totalPrice.subtract(commission);

        // Adding balance to ownerUser
        ownerUser.setBalance(ownerUser.getBalance().add(priceAfterCommission));

        // Subtracting Balance from clientUser
        clientUser.setBalance(clientUser.getBalance().subtract(totalPrice));

        // Getting the daily report and updating its values
        DailyReport dailyReport = dailyReportRepository.getDailyReportByDateRange(TimeFunctions.getStartOfDay(today), TimeFunctions.getEndOfDay(today));
        if (dailyReport == null) {
            throw new GeneralException(BogError.COULDNT_FIND_DAILY_REPORT_BY_PROVIDED_DATE_RANGE);
        }
        dailyReport.setTotalAmountSold(dailyReport.getTotalAmountSold().add(totalPrice));
        dailyReport.setTotalCommissionReceived(dailyReport.getTotalCommissionReceived().add(commission));
        dailyReport.setTotalUniqueProductsSold(dailyReport.getTotalUniqueProductsSold() + 1);
        dailyReportRepository.save(dailyReport);

        // Creating new PurchaseHistory for Reporting
        EcommerceUserProductsPurchaseHistory purchaseHistory = new EcommerceUserProductsPurchaseHistory();
        purchaseHistory.setDailyReport(dailyReport);
        purchaseHistory.setEcommerceUser(clientUser);
        purchaseHistory.setProducts(products);
        ecommerceUserProductsPurchaseHistoryRepository.save(purchaseHistory);

        // Saving the information
        userRepository.save(clientUser);
        userRepository.save(ownerUser);
        productsRepository.save(products);

    }

    @Override
    public List<ProductsGetBean> filter(ProductFilter productFilter) {

        StringBuilder searchBuilder = new StringBuilder();
        List<Products> productsList = new ArrayList<>();
        if (!StringUtils.isEmpty(productFilter.getProductName())) {
            searchBuilder.append(" AND upper(p.PRODUCT_NAME) LIKE upper(:keyword)");
        }
        if (productFilter.getProductPrice() != null && productFilter.getProductPrice().compareTo(new BigDecimal(0)) > 0) {
            searchBuilder.append(" AND p.PRODUCT_PRICE >= :productPrice");
        }
        if (productFilter.getProductQuantity() > 0) {
            searchBuilder.append(" AND p.PRODUCT_QUANTITY >= :productQuantity");
        }

        String filterQueryString =
                "SELECT p.id as productId, " +
                        "eu.email as email, " +
                        "p.PRODUCT_NAME as productName, " +
                        "p.PRODUCT_PRICE as productPrice, " +
                        "p.PRODUCT_QUANTITY as productQuantity, " +
                        "p.IMAGE_URL as imageUrl  " +
                        "FROM PRODUCTS P " +
                "JOIN ECOMMERCE_USER eu ON p.USER_ID = eu.id" +
                " WHERE 1=1 " + searchBuilder.toString();

        Query query = em.createNativeQuery(filterQueryString);
        if (!StringUtils.isEmpty(productFilter.getProductName())) {
            query.setParameter("keyword", "%".concat(productFilter.getProductName()).concat("%"));
        }
        if (productFilter.getProductPrice() != null && productFilter.getProductPrice().compareTo(new BigDecimal(0)) > 0) {
            query.setParameter("productPrice", productFilter.getProductPrice());
        }
        if (productFilter.getProductQuantity() > 0) {
            query.setParameter("productQuantity", productFilter.getProductQuantity());
        }

        query.unwrap(SQLQuery.class)
                .addScalar("productId", LongType.INSTANCE)
                .addScalar("email", StringType.INSTANCE)
                .addScalar("productName", StringType.INSTANCE)
                .addScalar("productPrice", BigDecimalType.INSTANCE)
                .addScalar("imageUrl", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(ProductsGetBean.class));
        List<ProductsGetBean> productsGetBeanList = (List<ProductsGetBean>) query.getResultList();

        return productsGetBeanList;
    }
}
