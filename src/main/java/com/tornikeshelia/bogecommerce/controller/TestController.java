package com.tornikeshelia.bogecommerce.controller;

import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public int numberPlusFive(@RequestParam(value = "number") int number){
        if (number < 5){
            throw new GeneralException(BogError.PROVIDED_ID_WAS_NULL);
        }
        return number+=5;
    }

}
