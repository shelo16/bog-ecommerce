package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.bean.purchasehistory.PurchaseHistoryExcelBean;
import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUserProductsPurchaseHistory;
import com.tornikeshelia.bogecommerce.model.persistence.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EcommerceUserProductsPurchaseHistoryRepository extends JpaRepository<EcommerceUserProductsPurchaseHistory, Long> {

}
