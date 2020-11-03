package com.tornikeshelia.bogecommerce.model.enums;

import lombok.Getter;

@Getter
public enum BogError {

    // ============= GENERAL ERRORS =========================

    OK("Success"),
    PROVIDED_ID_WAS_NULL("Provided Id was null"),
    INVALID_REQUEST("Invalid Request"),

    // ============= PRODUCT ERRORS =========================

    PRODUCT_SAVE_BEAN_WAS_NULL("Product save bean was null"),
    COULDNT_FIND_PRODUCT_BY_PROVIDED_ID("Couldn't find product by provided productId"),
    NO_PRODUCTS_FOUND("There are no products"),

    // ============= DAILY REPORT ERRORS =========================

    COULDNT_FIND_DAILY_REPORT_BY_PROVIDED_DATE_RANGE("Couldn't find DailyReport by provided Date Range"),

    // ============= ECOMMERCE_USER ERRORS =========================

    COULDNT_FIND_USER_BY_PROVIDED_ID("Couldn't find user with provided id"),
    COULDNT_FIND_USER_BY_PROVIDED_EMAIL("Couldn't find user with provided email"),
    COULDNT_FIND_USER_FROM_PROVIDED_PRODUCT("Couldn't find user from provided product"),
    USER_ALREADY_REGISTERED("User with provided email is already registered"),
    SHORT_LINK_IS_ALREADY_SENT_TO_YOUR_EMAIL("Shortlink is already sent to your email"),
    YOU_MUST_BE_AUTHENTICATED_TO_ADD_A_PRODUCT("You must be authenticated to add a product"),

    // ============= ECOMMERCE_USER_DETAILS ERRORS =========================

    COULDNT_FIND_USER_DETAILS_FROM_USER_ID("Couldn't find user details from user id"),
    USER_DOESNT_HAVE_ENOUGH_BALANCE("User doesn't have enough balance to purchase the product"),
    NOT_ENOUGH_PRODUCT_QUANTITY_LEFT_IN_STOCK("Not enough product quantity left in stock");

    private String description;

    BogError(String description) {
        this.description = description;
    }

}
