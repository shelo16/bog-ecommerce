package com.tornikeshelia.bogecommerce.model.bean.products;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsPurchaseBean {

    @NotNull
    @Min(1)
    private int productQuantity;

    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

}
