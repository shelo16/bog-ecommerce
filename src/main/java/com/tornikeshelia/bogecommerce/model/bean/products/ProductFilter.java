package com.tornikeshelia.bogecommerce.model.bean.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilter {

    private String productName;
    private BigDecimal productPrice;
    private int productQuantity;

}
