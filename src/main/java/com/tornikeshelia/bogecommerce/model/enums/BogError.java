package com.tornikeshelia.bogecommerce.model.enums;

import lombok.Getter;

@Getter
public enum BogError {

    // ============= GENERAL ERRORS =========================

    OK("Success"),
    PROVIDED_ID_WAS_NULL("Provided Id was null"),

    // ============= PRODUCT ERRORS =========================

    PRODUCT_SAVE_BEAN_WAS_NULL("Product save bean was null"),
    COULDNT_FIND_PRODUCT_BY_PROVIDED_ID("Couldn't find product by provided productId"),

    // ============= DAILY REPORT ERRORS =========================

    COULDNT_FIND_DAILY_REPORT_BY_PROVIDED_DATE_RANGE("Couldn't find DailyReport by provided Date Range");

    private String description;

    BogError(String description) {
        this.description = description;
    }

}
