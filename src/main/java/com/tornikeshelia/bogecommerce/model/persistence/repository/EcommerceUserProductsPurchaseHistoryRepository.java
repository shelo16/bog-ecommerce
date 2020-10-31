package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUserProductsPurchaseHistory;
import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcommerceUserProductsPurchaseHistoryRepository extends JpaRepository<EcommerceUserProductsPurchaseHistory,Long> {
}
