package com.tornikeshelia.bogecommerce.generator;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelGenerator {


//    public static void generateCompanyGridExcel() throws IOException {
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//
//        //Create a blank spreadsheet
//        XSSFSheet spreadsheet = workbook.createSheet("CompanyGridExcel");
//
//        XSSFRow row = null;
//        List<String> strings = new ArrayList<>();
//        strings.add("იურიდიული დასახელება");
//        strings.add("საიდენტიფიკაციო");
//        strings.add("სამართლებრივი ფორმა");
//        strings.add("კომპანიის სტატუსი");
//        strings.add("იდენტიფიკაციის სტატუსი");
//        strings.add("შუამავალი პროვაიდერი");
//        strings.add("მიმღები");
//        strings.add("მიმღების პროვაიდერი");
//
//
//        Map<String, Object[]> empInfo = new TreeMap<>();
//        empInfo.put("1", new Object[]{
//                strings
//        });
//
//        for (int i = 0; i < companySearchExcelBean.size(); i++) {
//            CompanySearchExcelBean company = companySearchExcelBean.get(i);
//            if (company == null) {
//                throw new GeneralException(CatalogErrors.EXCEL_GENERATION_COMPANY_WAS_NULL);
//            }
//            List<String> addInfoToMap = new ArrayList<>();
//            addInfoToMap.add(!StringUtils.isEmpty(company.getLegalName()) ? company.getLegalName() : "");
//            addInfoToMap.add(company.getCompanyIdentCode() != null ? company.getCompanyIdentCode().toString() : "");
//            addInfoToMap.add(!StringUtils.isEmpty(company.getLegalForm()) ? company.getLegalForm() : "");
//            addInfoToMap.add(!StringUtils.isEmpty(company.getCompanyStatus()) ? company.getCompanyStatus() : "");
//            addInfoToMap.add(!StringUtils.isEmpty(company.getIdentifyStatus()) ? company.getIdentifyStatus() : "");
//            addInfoToMap.add(!StringUtils.isEmpty(company.getIsMiddlemen()) ? company.getIsMiddlemen() : "");
//            addInfoToMap.add(!StringUtils.isEmpty(company.getIsReceiver()) ? company.getIsReceiver() : "");
//            addInfoToMap.add(!StringUtils.isEmpty(company.getIsReceiverProvider()) ? company.getIsReceiverProvider() : "");
//            empInfo.put("" + (i + 2), new Object[]{
//                    addInfoToMap
//            });
//        }
//
//        Set<String> keyid = empInfo.keySet();
//        int rowId = 0;
//
//        for (String key : keyid) {
//
//            row = spreadsheet.createRow(rowId++);
//            Object[] objectArr = empInfo.get(key);
//            int cellId = 0;
//            List<String> stringList = (List<String>) objectArr[0];
//            for (int i = 0; i < stringList.size(); i++) {
//
//                Cell cell = row.createCell(cellId++);
//                CellStyle style = workbook.createCellStyle();
//                XSSFFont font = workbook.createFont();
//
//                if (Integer.parseInt(key) == 1) {
//                    style.setWrapText(true);
//                    font.setBold(true);
//                    font.setFontHeight(10);
//                    style.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
//                } else {
//                    font.setFontHeight(9);
//                }
//
//                style.setAlignment(HorizontalAlignment.CENTER);
//                style.setVerticalAlignment(VerticalAlignment.CENTER);
//                style.setFont(font);
//                cell.setCellStyle(style);
//                cell.setCellValue(stringList.get(i));
//
//            }
//        }
//
////        if (row != null) {
////            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++)
////                workbook.getSheetAt(0).autoSizeColumn(colNum);
////        }
//
//        //Write the workbook in file system
//        FileOutputStream out = new FileOutputStream(new File("CompanyExcel.xlsx"));
//        response.setContentType("application/xlsx");
//        response.setHeader("Content-Disposition", "attachment; filename=" + "CompanyExcel.xlsx");
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Expires", "0");
//        workbook.write(out);
//
//        out.close();
//        workbook.write(response.getOutputStream());
//
//    }



}
