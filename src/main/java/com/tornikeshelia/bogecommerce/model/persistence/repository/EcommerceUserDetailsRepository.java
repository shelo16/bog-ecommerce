package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUserDetails;
import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcommerceUserDetailsRepository extends JpaRepository<EcommerceUserDetails,Long> {
}
