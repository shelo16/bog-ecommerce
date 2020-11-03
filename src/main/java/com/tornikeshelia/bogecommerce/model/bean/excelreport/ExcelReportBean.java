package com.tornikeshelia.bogecommerce.model.bean.excelreport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelReportBean {

    private int totalUniqueProductsSold;
    private BigDecimal totalAmountSold;
    private BigDecimal totalCommissionReceived;
    private int totalUniqueProductsAdded;
    private int totalUniqueAuthorizedUsers;
    private int totalVisitsOnWebPage;

}
