package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsSaveBean;
import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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
     * 1) if ProductSaveBean.getProductId is NULL -> the method will save new Product
     * else -> the method will update the existing Product;
     * 2) The method will try to update the dailyReport.getTotalUniqueProductsAdded()
     * reason that the method is not TRANSACTIONAL is to
     **/
    @Override
    @Transactional
    public Long saveProduct(ProductsSaveBean productsSaveBean) {
        if (productsSaveBean == null) {
            throw new GeneralException(BogError.PRODUCT_SAVE_BEAN_WAS_NULL);
        }
        Products products;
        LocalDate localDate = LocalDate.parse(new Date().toString());
        Date startOfDay = java.sql.Date.valueOf(String.valueOf(localDate.atStartOfDay()));
        Date endOfDay = java.sql.Date.valueOf(String.valueOf(LocalTime.MAX.atDate(localDate)));

        if (productsSaveBean.getProductId() == null) {
            products = new Products();
        } else {
            products = productsRepository.findById(productsSaveBean.getProductId())
                    .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_PRODUCT_BY_PROVIDED_ID));
        }

        BeanUtils.copyProperties(productsSaveBean, products);
        productsRepository.save(products);

        DailyReport dailyReport = dailyReportRepository.getDailyReportByDateRange(startOfDay, endOfDay);
        if (dailyReport == null) {
            throw new GeneralException(BogError.COULDNT_FIND_DAILY_REPORT_BY_PROVIDED_DATE_RANGE);
        } else {
            dailyReport.setTotalUniqueProductsAdded(dailyReport.getTotalUniqueProductsAdded() + 1);
            dailyReportRepository.save(dailyReport);
        }

        return products.getId();
    }


}
