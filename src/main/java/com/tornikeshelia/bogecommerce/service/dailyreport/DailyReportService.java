package com.tornikeshelia.bogecommerce.service.dailyreport;

import com.tornikeshelia.bogecommerce.model.bean.dailyreports.DailyReportSaveBean;

public interface DailyReportService {

    Long saveOrUpdate(DailyReportSaveBean dailyReportSaveBean);

}
