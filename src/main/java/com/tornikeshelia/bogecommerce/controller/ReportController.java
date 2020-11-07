package com.tornikeshelia.bogecommerce.controller;

import com.tornikeshelia.bogecommerce.service.excel.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/report")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ExcelService excelService;

    /**
     *
     * This controller method is for testing purposes only in swagger,
     * After going production this method should be deleted .
     *
     * **/
    @PostMapping(value = "/excel", produces = "application/xlsx")
    @Scheduled(cron = "0 0 0 * * *")
    public void generateReportExcel() throws IOException {
        excelService.generateExcel();
    }

}
