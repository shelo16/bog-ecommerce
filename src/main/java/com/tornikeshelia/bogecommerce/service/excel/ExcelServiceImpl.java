package com.tornikeshelia.bogecommerce.service.excel;

import com.tornikeshelia.bogecommerce.generator.TimeFunctions;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import com.tornikeshelia.bogecommerce.model.persistence.repository.DailyReportRepository;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserProductsPurchaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Service
public class ExcelServiceImpl implements ExcelService{

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private EcommerceUserProductsPurchaseHistoryRepository ecommerceUserProductsPurchaseHistoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void generateExcel() {
        Date today = new Date();
        Date startDate = TimeFunctions.getStartOfDay(today);
        Date endDate = TimeFunctions.getEndOfDay(today);
        DailyReport dailyReport = dailyReportRepository.getDailyReportByDateRange(startDate,endDate);

        String queryString =
                "SELECT p.PRODUCT_NAME as productName, " +
                        "p.PRODUCT_PRICE as productPrice, " +
                        "p.PRODUCT_QUANTITY as productQuantity, " +
                        "eu.EMAIL as ecommerceUserEmail, " +
                        "eu.PERSONAL_NUMBER as ecommerceUserPersonalNumber " +
                        "FROM ECOMMERCE_USER_PRODUCTS_PURCHASE_HISTORY history " +
                        "JOIN PRODUCTS p on p.id = history.PRODUCTS_ID " +
                        "JOIN ECOMMERCE_USER eu ON eu.id = history.USER_ID " +
                "WHERE history.DAILY_REPORT_ID =:drId AND history.CREATION_DATE>=:startDate AND history.CREATION_DATE<=:endDate";
        em.createNativeQuery(queryString);
    }
}
