package com.tornikeshelia.bogecommerce.model.persistence.repository;

import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {

    @Query(value = "FROM DailyReport dr WHERE dr.creationDate >=:startDate AND dr.creationDate <=:endDate")
    DailyReport getDailyReportByDateRange(Date startDate, Date endDate);

}
