package com.tornikeshelia.bogecommerce.security.controller;


import com.tornikeshelia.bogecommerce.security.model.register.ShortLinkRequest;
import com.tornikeshelia.bogecommerce.security.model.register.ShortLinkResponse;
import com.tornikeshelia.bogecommerce.security.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/reg")
public class RegistrationController {


    @Value("${short-link-url}")
    private String shortLinkUrl;

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping(value = "/short-link")
    public String generateShortLink(@RequestParam("email") String email){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer 6e43cb182d7a93e0daa81166568f830af7d287e9");
        HttpEntity<ShortLinkRequest> request = new HttpEntity<>(new ShortLinkRequest("bit.ly","http://github.com/shelo16"),headers);


        ResponseEntity<ShortLinkResponse> response = restTemplate
                .exchange(shortLinkUrl, HttpMethod.POST, request, ShortLinkResponse.class);

        emailService.sendSimpleMessage(email,"Test","Hi");

        return response.getBody().getLink();
    }

}
