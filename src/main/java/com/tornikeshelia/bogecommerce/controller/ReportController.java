package com.tornikeshelia.bogecommerce.controller;

import com.tornikeshelia.bogecommerce.service.excel.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/report")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ExcelService excelService;

    @PostMapping(value = "/excel", produces = "application/xlsx")
    public void generateReportExcel(HttpServletResponse response) throws IOException {
        excelService.generateExcel(response);
    }

}
