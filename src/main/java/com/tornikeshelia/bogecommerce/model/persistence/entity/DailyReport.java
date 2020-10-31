package com.tornikeshelia.bogecommerce.model.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DAILY_REPORT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "TOTAL_UNIQUE_PRODUCTS_SOLD")
    private int totalUniqueProductsSold;

    @Column(name = "TOTAL_AMOUNT_SOLD")
    private BigDecimal totalAmountSold;

    @Column(name = "TOTAL_COMMISSION_RECEIVED")
    private BigDecimal totalCommissionReceived;

    @Column(name = "TOTAL_UNIQUE_PRODUCTS_ADDED")
    private int totalUniqueProductsAdded;

    @Column(name = "TOTAL_UNIQUE_AUTHORIZED_USERS")
    private int totalUniqueAuthorizedUsers;

    @Column(name = "TOTAL_VISITS_ON_WEBPAGE")
    private int totalVisitsOnWebPage;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date(new java.util.Date().getTime());
    }

}
