package com.tornikeshelia.bogecommerce.security.service.registration;

import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserRepository;
import com.tornikeshelia.bogecommerce.security.model.register.ConfirmUserBean;
import com.tornikeshelia.bogecommerce.security.model.register.RegisterUserBean;
import com.tornikeshelia.bogecommerce.security.model.register.ShortLinkRequest;
import com.tornikeshelia.bogecommerce.security.model.register.ShortLinkResponse;
import com.tornikeshelia.bogecommerce.security.service.EmailServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private EcommerceUserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Value("${short-link-url}")
    private String shortLinkUrl;

    @Override
    public void generateShortLink(RegisterUserBean registerUserBean) {

        EcommerceUser ecommerceUser = userRepository.searchByEmail(registerUserBean.getEmail());
        if (ecommerceUser != null) {
            throw new GeneralException(BogError.USER_ALREADY_REGISTERED);
        }else{
            ecommerceUser = new EcommerceUser();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer 6e43cb182d7a93e0daa81166568f830af7d287e9");
        HttpEntity<ShortLinkRequest> request = new HttpEntity<>(new ShortLinkRequest("bit.ly", "http://github.com/shelo16"), headers);

        ResponseEntity<ShortLinkResponse> response = restTemplate
                .exchange(shortLinkUrl, HttpMethod.POST, request, ShortLinkResponse.class);

        BeanUtils.copyProperties(registerUserBean,ecommerceUser);
        ecommerceUser.setBalance(new BigDecimal(0));
        ecommerceUser.setIsValid(0L);
        userRepository.save(ecommerceUser);
        emailService.sendSimpleMessage(registerUserBean.getEmail(), "Confirm Registration", "http://localhost:4200/confirm?id="+ecommerceUser.getId());

    }

    @Override
    public void confirmRegistration(ConfirmUserBean confirmUserBean) {

        EcommerceUser ecommerceUser = userRepository.searchNotValidUserById(confirmUserBean.getUserId());
        if (ecommerceUser == null){
            throw new GeneralException(BogError.INVALID_REQUEST);
        }

        ecommerceUser.setBcryptedPassword(bCryptPasswordEncoder.encode(confirmUserBean.getPassword()));
        ecommerceUser.setIsValid(1L);
        userRepository.save(ecommerceUser);
    }
}
