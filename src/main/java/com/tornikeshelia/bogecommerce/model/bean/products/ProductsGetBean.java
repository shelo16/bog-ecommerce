package com.tornikeshelia.bogecommerce.model.bean.products;


import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsGetBean {

    private Long productId;

    private String email;

    private String productName;

    private BigDecimal productPrice;

    private int productQuantity;

    private String imageUrl;

    public static ProductsGetBean transformProductsEntity(Products products) {
        return ProductsGetBean.builder()
                .productId(products.getId())
                .email(products.getEcommerceUser().getEmail())
                .productName(products.getProductName())
                .productPrice(products.getProductPrice())
                .productQuantity(products.getProductQuantity())
                .imageUrl(products.getImageUrl())
                .build();
    }

}
