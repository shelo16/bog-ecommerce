package com.tornikeshelia.bogecommerce.service.excel;

import com.tornikeshelia.bogecommerce.generator.ExcelGenerator;
import com.tornikeshelia.bogecommerce.generator.TimeFunctions;
import com.tornikeshelia.bogecommerce.model.bean.excelreport.ExcelReportBean;
import com.tornikeshelia.bogecommerce.model.bean.purchasehistory.PurchaseHistoryExcelBean;
import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import com.tornikeshelia.bogecommerce.model.persistence.repository.DailyReportRepository;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserProductsPurchaseHistoryRepository;
import com.tornikeshelia.bogecommerce.security.service.EmailServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void generateExcel(HttpServletResponse response) throws IOException {
        Date today = new Date();
        Date startDate = TimeFunctions.getStartOfDay(today);
        Date endDate = TimeFunctions.getEndOfDay(today);
        DailyReport dailyReport = dailyReportRepository.getDailyReportByDateRange(startDate, endDate);
        DailyReport newDailyReport = new DailyReport(0,new BigDecimal(0),new BigDecimal(0),0,0,0);
        dailyReportRepository.save(newDailyReport);
        if (dailyReport == null) {
            throw new GeneralException(BogError.COULDNT_FIND_DAILY_REPORT_BY_PROVIDED_DATE_RANGE);
        }

        String queryString =
                "SELECT p.PRODUCT_NAME as productName, " +
                        "p.PRODUCT_PRICE as productPrice, " +
                        "p.PRODUCT_QUANTITY as productQuantity, " +
                        "eu.EMAIL as ecommerceUserEmail, " +
                        "eu.PERSONAL_NUMBER as ecommerceUserPersonalNumber " +
                        "FROM ECOMMERCE_USER_PRODUCTS_PURCHASE_HISTORY history " +
                        "JOIN PRODUCTS p on p.id = history.PRODUCTS_ID " +
                        "JOIN ECOMMERCE_USER eu ON eu.id = history.USER_ID " +
                        "WHERE history.DAILY_REPORT_ID =:drId";
        Query query = em.createNativeQuery(queryString);
        query.setParameter("drId", dailyReport.getId());
        query.unwrap(SQLQuery.class)
                .addScalar("productName", StringType.INSTANCE)
                .addScalar("productPrice", BigDecimalType.INSTANCE)
                .addScalar("productQuantity", IntegerType.INSTANCE)
                .addScalar("ecommerceUserEmail", StringType.INSTANCE)
                .addScalar("ecommerceUserPersonalNumber", StringType.INSTANCE)
                .setResultTransformer(Transformers.aliasToBean(PurchaseHistoryExcelBean.class));
        List<PurchaseHistoryExcelBean> purchaseHistoryExcelBeanList = query.getResultList();
        File file = ExcelGenerator.generateReportExcel(new ExcelReportBean(dailyReport,purchaseHistoryExcelBeanList),response);

        emailService.sendMessageWithAttachment("tornikeshelia1996@gmail.com","Excel Daily Report","Download the Excel in attachment",
                FileUtils.readFileToByteArray(file),file.getName());

    }
}
