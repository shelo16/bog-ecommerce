package com.tornikeshelia.bogecommerce.model.enums;

import lombok.Getter;

@Getter
public enum BogError {
    OK("Success"),
    PROVIDED_ID_WAS_NULL("Provided Id was null");

    private String description;

    BogError(String description) {
        this.description = description;
    }

}
