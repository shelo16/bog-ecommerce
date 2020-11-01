package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.generator.TimeFunctions;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsPurchaseBean;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    /**
     * Save Or Update Method :
     * 1) if ProductBean.getProductId is NULL -> the method will save new Product
     * else -> the method will update the existing Product;
     * 2) The method will try to update the dailyReport.getTotalUniqueProductsAdded()
     * reason that the method is not TRANSACTIONAL is to
     **/
    @Override
    @Transactional
    public Long saveProduct(ProductsBean productsBean) {

        Products products;
        Date today = new Date();

        if (productsBean.getProductId() == null) {
            // Creates New
            products = new Products();
        } else {
            // Gets Existing For Update
            products = productsRepository.findById(productsBean.getProductId())
                    .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));
        }

        EcommerceUser user = userRepository.findById(productsBean.getEcommerceUserId())
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_USER_BY_PROVIDED_ID));
        products.setEcommerceUser(user);
        BeanUtils.copyProperties(productsBean, products);
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
     * Searches products using findById Jpa method -> transforms the product to ProductsBean
     **/
    @Override
    public ProductsBean getById(Long id) {

        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));
        return ProductsBean.transformProductsEntity(products);

    }

    /**
     * Searches for ALL the products in DB and transforms them to ProductsBean
     **/
    @Override
    public List<ProductsBean> getAll() {
        List<Products> productsList = productsRepository.findAll();
        if (productsList.isEmpty()) {
            throw new GeneralException(BogError.NO_PRODUCTS_FOUND);
        }

        return productsList.stream()
                .map(ProductsBean::transformProductsEntity)
                .collect(Collectors.toList());
    }

    /**
     * Searches for ALL the products in DB by userId and transforms them to ProductsBean
     **/
    @Override
    public List<ProductsBean> getByUserId(Long userId) {
        List<Products> productsList = productsRepository.getByUserId(userId);

        if (productsList.isEmpty()) {
            throw new GeneralException(BogError.NO_PRODUCTS_FOUND);
        }

        return productsList.stream()
                .map(ProductsBean::transformProductsEntity)
                .collect(Collectors.toList());
    }

    /**
     * Transactional Method for Purchasing
     * 1 -> Finds the necessary information (e.g. products, ownerUser , clientUser , dailyReport ) and checks that values aren't NULL
     * 2 -> Calculates totalPrice of purchase and checks if clientUser has enough on the balance
     * 3 -> Checks totalQuantity validity , Subtracts totalQuantity left to product and calculates Commission and PriceAfterCommission
     * 4 -> Add's priceAfterCommission to owner of the product , subtracts TotalPrice from client's balance
     * 5 -> Edits info of DailyReport and Creates new PurchaseHistory
     * 6 -> Saves all the info to DB
     **/
    @Override
    @Transactional
    public void purchaseProduct(ProductsPurchaseBean productsPurchaseBean) {

        Date today = new Date();

        // Setting up necessary objects
        Products products = productsRepository.findById(productsPurchaseBean.getProductId())
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));

        // Checking if Owner userId and client userId's are not null
        if (products.getEcommerceUser() == null || productsPurchaseBean.getUserId() == null) {
            throw new GeneralException(BogError.COULDNT_FIND_USER_FROM_PROVIDED_PRODUCT);
        }

        EcommerceUser clientUser = userRepository.findById(productsPurchaseBean.getUserId())
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_USER_DETAILS_FROM_USER_ID));

        EcommerceUser ownerUser = userRepository.findById(products.getEcommerceUser().getId())
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_USER_DETAILS_FROM_USER_ID));


        // Calculating total price
        BigDecimal totalPrice = products.getProductPrice().multiply(new BigDecimal(products.getProductQuantity()));

        // Checking if clientUser has enough on balance
        if (clientUser.getBalance().compareTo(totalPrice) < 0) {
            throw new GeneralException(BogError.USER_DOESNT_HAVE_ENOUGH_BALANCE);
        }

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
}
