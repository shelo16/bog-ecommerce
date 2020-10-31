package com.tornikeshelia.bogecommerce.model.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ECOMMERCE_USER_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "PERSONAL_NUMBER", nullable = false)
    private String personalNumber;

    @Column(name = "IBAN", nullable = false)
    private String iban;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

}
