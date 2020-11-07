package com.tornikeshelia.bogecommerce.generator;


import com.tornikeshelia.bogecommerce.model.bean.excelreport.ExcelReportBean;
import com.tornikeshelia.bogecommerce.model.bean.purchasehistory.PurchaseHistoryExcelBean;
import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.entity.DailyReport;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelGenerator {


    public static File generateReportExcel(ExcelReportBean excelReportBean) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        DailyReport dailyReport = excelReportBean.getDailyReport();

        //Create a blank spreadsheet
        XSSFSheet spreadsheet = workbook.createSheet("DailyReportSheet");

        XSSFRow row = null;
        List<String> strings = new ArrayList<>();
        strings.add("შესრულებული შესყიდვების რაოდენობა");
        strings.add("შესყიდული პროდუქციის ღირებულების ჯამური თანხა");
        strings.add("შესყიდვებით მიღებული საკომისიო");
        strings.add("დამატებული პროდუქციის რაოდენობა");
        strings.add("მოცემულ დღეს უნიკალური მომხმარებლის რაოდენობა, რომელთანც გაიარეს ავტორიზაცია");
        strings.add("მოცემულ დღეს ვებგვერდზე შემოსვლების რაოდენობა");

        Map<String, Object[]> empInfo = new TreeMap<>();
        empInfo.put("1", new Object[]{
                strings
        });

        List<String> addInfoToMap = new ArrayList<>();
        addInfoToMap.add(String.valueOf(dailyReport.getTotalUniqueProductsSold()));
        addInfoToMap.add(dailyReport.getTotalAmountSold() != null ? dailyReport.getTotalAmountSold().toString() : "");
        addInfoToMap.add(dailyReport.getTotalCommissionReceived() != null ? dailyReport.getTotalCommissionReceived().toString() : "");
        addInfoToMap.add(String.valueOf(dailyReport.getTotalUniqueProductsAdded()));
        addInfoToMap.add(String.valueOf(dailyReport.getTotalUniqueAuthorizedUsers()));
        addInfoToMap.add(String.valueOf(dailyReport.getTotalVisitsOnWebPage()));
        empInfo.put("2", new Object[]{
                addInfoToMap
        });


        Set<String> keyid = empInfo.keySet();
        int rowId = 0;

        for (String key : keyid) {

            row = spreadsheet.createRow(rowId++);
            Object[] objectArr = empInfo.get(key);
            int cellId = 0;
            List<String> stringList = (List<String>) objectArr[0];
            for (int i = 0; i < stringList.size(); i++) {

                Cell cell = row.createCell(cellId++);
                CellStyle style = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();

                if (Integer.parseInt(key) == 1) {
                    style.setWrapText(true);
                    font.setBold(true);
                    font.setFontHeight(10);
                    style.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                } else {
                    font.setFontHeight(9);
                }

                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setFont(font);
                cell.setCellStyle(style);
                cell.setCellValue(stringList.get(i));

            }
        }

        if (row != null) {
            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++)
                workbook.getSheetAt(0).autoSizeColumn(colNum);
        }


        List<PurchaseHistoryExcelBean> purchaseHistoryExcelBeanList = excelReportBean.getPurchaseHistoryExcelBeanList();
        XSSFSheet purchaseHistorySheet = workbook.createSheet("PurchaseHistorySheet");
        List<String> purchaseStrings = new ArrayList<>();
        purchaseStrings.add("პროდუქტის სახელი");
        purchaseStrings.add("პროდუქტის ფასი");
        purchaseStrings.add("რაოდენობა");
        purchaseStrings.add("კლიენტის მეილი");
        purchaseStrings.add("კლიენტის პირადი ნომერი");

        Map<String, Object[]> newEmpInfo = new TreeMap<>();
        newEmpInfo.put("1", new Object[]{
                purchaseStrings
        });

        for (int i = 0; i < purchaseHistoryExcelBeanList.size(); i++) {
            PurchaseHistoryExcelBean history = purchaseHistoryExcelBeanList.get(i);
            if (history == null) {
                throw new GeneralException(BogError.EXCEL_GENERATION_PURCHASE_HISTORY_WAS_NULL);
            }
            List<String> newAddInfoToMap = new ArrayList<>();
            newAddInfoToMap.add(!StringUtils.isEmpty(history.getProductName()) ? history.getProductName() : "");
            newAddInfoToMap.add(history.getProductPrice() != null ? history.getProductPrice().toString() : "");
            newAddInfoToMap.add(String.valueOf(history.getProductQuantity()));
            newAddInfoToMap.add(!StringUtils.isEmpty(history.getEcommerceUserEmail()) ? history.getEcommerceUserEmail() : "");
            newAddInfoToMap.add(!StringUtils.isEmpty(history.getEcommerceUserPersonalNumber()) ? history.getEcommerceUserPersonalNumber() : "");
            newEmpInfo.put("" + (i + 2), new Object[]{
                    newAddInfoToMap
            });
        }


        Set<String> newKeyid = newEmpInfo.keySet();
        int newRowId = 0;
        for (String key : newKeyid) {

            row = purchaseHistorySheet.createRow(newRowId++);
            Object[] objectArr = newEmpInfo.get(key);
            int cellId = 0;
            List<String> stringList = (List<String>) objectArr[0];
            for (int i = 0; i < stringList.size(); i++) {

                Cell cell = row.createCell(cellId++);
                CellStyle style = workbook.createCellStyle();
                XSSFFont font = workbook.createFont();

                if (Integer.parseInt(key) == 1) {
                    style.setWrapText(true);
                    font.setBold(true);
                    font.setFontHeight(10);
                    style.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                } else {
                    font.setFontHeight(9);
                }

                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setFont(font);
                cell.setCellStyle(style);
                cell.setCellValue(stringList.get(i));

            }
        }

        if (row != null) {
            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++)
                workbook.getSheetAt(1).autoSizeColumn(colNum);
        }

        //Write the workbook in file system
        FileOutputStream out = new FileOutputStream(new File("BogReportExcel.xlsx"));

        workbook.write(out);

        out.close();
        return new File("BogReportExcel.xlsx");

    }


}
