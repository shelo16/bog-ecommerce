package com.tornikeshelia.bogecommerce.security.controller;


import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserRepository;
import com.tornikeshelia.bogecommerce.security.model.register.ConfirmUserBean;
import com.tornikeshelia.bogecommerce.security.model.register.RegisterUserBean;
import com.tornikeshelia.bogecommerce.security.model.register.ShortLinkRequest;
import com.tornikeshelia.bogecommerce.security.model.register.ShortLinkResponse;
import com.tornikeshelia.bogecommerce.security.service.EmailServiceImpl;
import com.tornikeshelia.bogecommerce.security.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/reg")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/short-link")
    public void generateShortLink(@Valid @NotNull @RequestBody RegisterUserBean registerUserBean) {
        registrationService.generateShortLink(registerUserBean);
    }

    @PostMapping(value = "/confirm")
    public void confirmRegistration(@Valid @NotNull @RequestBody ConfirmUserBean confirmUserBean) {
        registrationService.confirmRegistration(confirmUserBean);
    }

}
