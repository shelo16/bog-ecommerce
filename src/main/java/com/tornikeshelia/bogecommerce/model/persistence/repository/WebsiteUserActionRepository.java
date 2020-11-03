package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.WebsiteUserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface WebsiteUserActionRepository extends JpaRepository<WebsiteUserAction,Long> {

    @Query(value = "FROM WebsiteUserAction wua WHERE wua.creationDate>=:startDate and wua.creationDate<=:endDate and wua.isAuthorized =:isAuthorized and wua.email=:email")
    WebsiteUserAction getByDateRangeByAuthorizedAndEmail(Date startDate, Date endDate, Long isAuthorized, String email);

}
