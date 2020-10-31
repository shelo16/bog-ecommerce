package com.tornikeshelia.bogecommerce.model.bean.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsSaveBean {

    private Long productId;

    @NotNull
    @Min(1)
    private Long ecommerceUserId;

    @NotEmpty(message = "{name.empty}")
    private String productName;

    @NotNull
    private BigDecimal productPrice;

    @Min(1)
    private int productQuantity;

    @NotEmpty(message = "{name.empty}")
    private String imageUrl;

}
