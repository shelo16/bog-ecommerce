package com.tornikeshelia.bogecommerce.model.bean.products;

import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsSaveBean {

    private Long productId;

    @NotEmpty(message = "{name.empty}")
    private String productName;

    @NotNull
    private BigDecimal productPrice;

    @Min(1)
    private int productQuantity;

    @NotNull(message = "{name.null}")
    private File imageFile;

    public static ProductsSaveBean transformProductsEntity(Products products) {
        return ProductsSaveBean.builder()
                .productId(products.getId())
                .productName(products.getProductName())
                .productPrice(products.getProductPrice())
                .productQuantity(products.getProductQuantity())
                .build();
    }

}
