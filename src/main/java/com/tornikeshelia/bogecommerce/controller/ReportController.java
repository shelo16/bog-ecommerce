package com.tornikeshelia.bogecommerce.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/report")
@CrossOrigin(origins = "*")
public class ReportController {

    @PostMapping(value = "/excel", produces = "application/xlsx")
    public void generateReportExcel() {

    }

}
