package com.tornikeshelia.bogecommerce.model.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ECOMMERCE_USER_PRODUCTS_PURCHASE_HISTORY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceUserProductsPurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private EcommerceUser ecommerceUser;

    @ManyToOne
    @JoinColumn(name = "PRODUCTS_ID", nullable = false)
    private Products products;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date(new java.util.Date().getTime());
    }


}
