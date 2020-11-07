package com.tornikeshelia.bogecommerce.model.bean.excelreport;

import com.tornikeshelia.bogecommerce.model.bean.purchasehistory.PurchaseHistoryExcelBean;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelReportBean {

    private DailyReport dailyReport;
    private List<PurchaseHistoryExcelBean> purchaseHistoryExcelBeanList;

}
