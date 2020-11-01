package com.tornikeshelia.bogecommerce.model.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ECOMMERCE_USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "BCRYPTED_PASWORD")
    private String bcryptedPassword;

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

    @Column(name = "IS_VALID")
    private Long isValid;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date(new java.util.Date().getTime());
    }

}
