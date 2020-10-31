package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products,Long> {
}
