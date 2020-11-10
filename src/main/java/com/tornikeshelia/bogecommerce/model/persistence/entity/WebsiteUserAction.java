package com.tornikeshelia.bogecommerce.model.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WEBSITE_USER_ACTION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteUserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_AUTHORIZED")
    private Long isAuthorized;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date(new java.util.Date().getTime());
    }

    public WebsiteUserAction(String email, Long isAuthorized) {
        this.email = email;
        this.isAuthorized = isAuthorized;
    }
}
