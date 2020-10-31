package com.tornikeshelia.bogecommerce.service.dailyreport;

import com.tornikeshelia.bogecommerce.model.bean.dailyreports.DailyReportSaveBean;
import com.tornikeshelia.bogecommerce.model.persistence.repository.DailyReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyReportServiceImpl implements DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Override
    public Long saveOrUpdate(DailyReportSaveBean dailyReportSaveBean) {
        return null;
    }
}
