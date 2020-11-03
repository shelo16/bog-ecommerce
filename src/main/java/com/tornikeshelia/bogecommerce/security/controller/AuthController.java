package com.tornikeshelia.bogecommerce.security.controller;

import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserRepository;
import com.tornikeshelia.bogecommerce.security.model.bean.authentication.AuthenticationRequest;
import com.tornikeshelia.bogecommerce.security.model.bean.authentication.AuthenticationResponse;
import com.tornikeshelia.bogecommerce.security.model.bean.checkuser.CheckUserAuthResponse;
import com.tornikeshelia.bogecommerce.security.model.bean.register.RegisterUserBean;
import com.tornikeshelia.bogecommerce.security.service.JwtUtilService;
import com.tornikeshelia.bogecommerce.security.service.MyUserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private EcommerceUserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    /**
     *
     * 1 ) Authenticates User if username and password are correct
     * 2 ) Creates JWT token and stores it as HttpOnly cookie
     *
     * **/
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public CheckUserAuthResponse authenticateUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception {

        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken
                            (authenticationRequest.getEmail(),
                                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtUtilService.generateToken(userDetails);
        Cookie cookie = new Cookie("token", jwt);
        cookie.setPath("/");
        cookie.setMaxAge(10 * 10 * 10 * 10 * 10);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        res.addCookie(cookie);
        return new CheckUserAuthResponse(true,authenticationRequest.getEmail());

    }

    @RequestMapping(value = "/checkAuth", method = RequestMethod.GET)
    public CheckUserAuthResponse checkIfUserIsAuthenticated(HttpServletRequest request){
        String jwtToken = null;
        String email = null;
        Boolean isAuthenticated = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                    email = jwtUtilService.extractUsername(jwtToken);
                    isAuthenticated = jwtUtilService.validateToken(jwtToken);
                    break;
                }
            }
        }
        return new CheckUserAuthResponse(isAuthenticated,email);
    }

    /**
     *
     * Logout Logic :
     * Checks if Cookie has a "token" value inside (which is a JWT token)
     * if true -> gets the token and sets its expiration date as NOW, which basically deletes the cookie
     *
     * **/
    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void logout(HttpServletResponse response, HttpServletRequest request) {

        String jwtToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

}
