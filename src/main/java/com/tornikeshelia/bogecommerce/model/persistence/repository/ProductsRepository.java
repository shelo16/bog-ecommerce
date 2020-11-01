package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Products,Long> {

    @Query(value = "SELECT * FROM PRODUCTS p WHERE p.USER_ID =:userId", nativeQuery = true)
    List<Products> getByUserId(Long userId);

}
