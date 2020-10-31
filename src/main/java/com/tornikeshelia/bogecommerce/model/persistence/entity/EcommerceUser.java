package com.tornikeshelia.bogecommerce.model.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(name = "EMAIL",nullable = false)
    private String email;

    @Column(name = "BCRYPTED_PASWORD",nullable = false)
    private String bcryptedPassword;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ECOMMERCE_USER_DETAILS_ID")
    private EcommerceUserDetails ecommerceUserDetails;

    @Column(name = "ECOMMERCE_USER_DETAILS_ID", updatable = false, insertable = false)
    private Long ecommerceUserDetailsId;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date(new java.util.Date().getTime());
    }

}
