package com.tornikeshelia.bogecommerce.model.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private EcommerceUser ecommerceUser;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "PRODUCT_PRICE", nullable = false)
    private BigDecimal productPrice;

    @Column(name = "PRODUCT_QUANTITY", nullable = false)
    private int productQuantity;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date(new java.util.Date().getTime());
    }

}
