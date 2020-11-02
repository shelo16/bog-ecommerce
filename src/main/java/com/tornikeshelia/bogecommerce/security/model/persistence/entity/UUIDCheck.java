package com.tornikeshelia.bogecommerce.security.model.persistence.entity;

import com.tornikeshelia.bogecommerce.security.model.enums.UuidTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UUID_CHECK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UUIDCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "IS_VALID")
    private Long isValid;

    @Column(name = "UUID_TYPE")
    private UuidTypeEnum uuidType;

    @PrePersist
    protected void onCreate() {
        creationDate = new Date(new java.util.Date().getTime());
    }

    public UUIDCheck(Long userId, String uuid, Long isValid, UuidTypeEnum uuidType) {
        this.userId = userId;
        this.uuid = uuid;
        this.isValid = isValid;
        this.uuidType = uuidType;
    }
}
