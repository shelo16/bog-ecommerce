package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.generator.TimeFunctions;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;
import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
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

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
        if (productsBean == null) {
            throw new GeneralException(BogError.PRODUCT_SAVE_BEAN_WAS_NULL);
        }
        Products products;
        Date today = new Date();

        if (productsBean.getProductId() == null) {
            products = new Products();
        } else {
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

        if (id == null) {
            throw new GeneralException(BogError.PROVIDED_ID_WAS_NULL);
        }

        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));
        return ProductsBean.transformProductsEntity(products);
    }
}
