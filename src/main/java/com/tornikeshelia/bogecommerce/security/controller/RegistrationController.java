package com.tornikeshelia.bogecommerce.security.controller;


import com.tornikeshelia.bogecommerce.security.model.bean.register.AuthBean;
import com.tornikeshelia.bogecommerce.security.model.bean.register.RegisterUserBean;
import com.tornikeshelia.bogecommerce.security.model.bean.register.ResetPasswordBean;
import com.tornikeshelia.bogecommerce.security.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/reg")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/register-short-link")
    public void generateRegisterShortLink(@Valid @NotNull @RequestBody RegisterUserBean registerUserBean) {
        registrationService.generateRegisterShortLink(registerUserBean);
    }

    @PostMapping(value = "/confirm")
    public void confirmAuth(@Valid @NotNull @RequestBody AuthBean authBean) {
        registrationService.confirmAuth(authBean);
    }

    @PostMapping(value = "/reset-short-link")
    public void generateResetShortLink(@Valid @NotNull @RequestBody ResetPasswordBean resetPasswordBean) {
        registrationService.generateResetShortLink(resetPasswordBean);
    }

}
