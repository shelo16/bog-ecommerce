package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcommerceUserRepository extends JpaRepository<EcommerceUser,Long> {
}
