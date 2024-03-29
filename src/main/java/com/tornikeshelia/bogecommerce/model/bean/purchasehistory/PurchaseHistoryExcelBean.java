package com.tornikeshelia.bogecommerce.model.bean.purchasehistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryExcelBean {

    private String productName;
    private BigDecimal productPrice;
    private int productQuantity;
    private String ecommerceUserEmail;
    private String ecommerceUserPersonalNumber;

}
