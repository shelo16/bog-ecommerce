package com.tornikeshelia.bogecommerce.model.exception;

import com.tornikeshelia.bogecommerce.model.enums.BogError;
import lombok.*;


@Getter
@Setter
public class GeneralException extends RuntimeException {
    private BogError bogError;

    public GeneralException(BogError bogError) {
        this.bogError = bogError;
    }

}
