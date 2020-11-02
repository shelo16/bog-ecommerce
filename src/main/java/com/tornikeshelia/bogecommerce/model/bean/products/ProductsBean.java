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
public class ProductsBean {

    private Long productId;

    @NotNull(message = "{name.null}")
    @Min(1)
    private Long ecommerceUserId;

    @NotEmpty(message = "{name.empty}")
    private String productName;

    @NotNull
    private BigDecimal productPrice;

    @Min(1)
    private int productQuantity;

    @NotNull(message = "{name.null}")
    private File imageFile;

    private String imageUrl;

    public static ProductsBean transformProductsEntity(Products products) {
        return ProductsBean.builder()
                .productId(products.getId())
                .ecommerceUserId(products.getEcommerceUser().getId())
                .productName(products.getProductName())
                .productPrice(products.getProductPrice())
                .productQuantity(products.getProductQuantity())
                .imageUrl(products.getImageUrl())
                .build();
    }

}
