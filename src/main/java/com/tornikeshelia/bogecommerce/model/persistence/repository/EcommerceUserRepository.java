package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EcommerceUserRepository extends JpaRepository<EcommerceUser, Long> {

    @Query(value = "FROM EcommerceUser eu WHERE eu.email =:email")
    EcommerceUser searchByEmail(String email);

    @Query(value = "FROM EcommerceUser eu WHERE eu.id =:id AND eu.isValid = 0")
    EcommerceUser searchNotValidUserById(Long id);

}
