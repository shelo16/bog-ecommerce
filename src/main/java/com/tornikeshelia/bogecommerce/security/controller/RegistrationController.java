package com.tornikeshelia.bogecommerce.security.controller;


import com.tornikeshelia.bogecommerce.security.model.bean.register.AuthBean;
import com.tornikeshelia.bogecommerce.security.model.bean.register.RegisterUserBean;
import com.tornikeshelia.bogecommerce.security.model.bean.register.ResetPasswordBean;
import com.tornikeshelia.bogecommerce.security.service.registration.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/reg")
public class RegistrationController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register-short-link")
    public void generateRegisterShortLink(@Valid @NotNull @RequestBody RegisterUserBean registerUserBean) {
        authService.generateRegisterShortLink(registerUserBean);
    }

    @PostMapping(value = "/confirm")
    public void confirmAuth(@Valid @NotNull @RequestBody AuthBean authBean) {
        authService.confirmAuth(authBean);
    }

    @PostMapping(value = "/reset-short-link")
    public void generateResetShortLink(@Valid @NotNull @RequestBody ResetPasswordBean resetPasswordBean) {
        authService.generateResetShortLink(resetPasswordBean);
    }

}
