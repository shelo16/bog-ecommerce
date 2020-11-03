package com.tornikeshelia.bogecommerce.security.controller;

import com.tornikeshelia.bogecommerce.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.bogecommerce.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.bogecommerce.security.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public CheckUserAuthResponse authenticateUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception {

        return authenticationService.authenticateUser(authenticationRequest, res);

    }

    @RequestMapping(value = "/checkAuth", method = RequestMethod.GET)
    public CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request) {

        return authenticationService.checkIfUserIsAuthenticated(request);

    }


    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void logout(HttpServletResponse response, HttpServletRequest request) {

        authenticationService.logout(response, request);

    }

}
