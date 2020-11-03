package com.tornikeshelia.bogecommerce.security.service.registration;

import com.tornikeshelia.bogecommerce.model.enums.BogError;
import com.tornikeshelia.bogecommerce.model.exception.GeneralException;
import com.tornikeshelia.bogecommerce.model.persistence.entity.EcommerceUser;
import com.tornikeshelia.bogecommerce.model.persistence.repository.EcommerceUserRepository;
import com.tornikeshelia.bogecommerce.security.model.bean.register.*;
import com.tornikeshelia.bogecommerce.security.model.enums.UuidTypeEnum;
import com.tornikeshelia.bogecommerce.security.model.persistence.entity.UUIDCheck;
import com.tornikeshelia.bogecommerce.security.model.persistence.repository.UUIDCheckRepository;
import com.tornikeshelia.bogecommerce.security.service.EmailServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private EcommerceUserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UUIDCheckRepository uuidRepository;

    @Value("${short-link-url}")
    private String shortLinkUrl;


    /**
     *
     * GenerateRegisterShortLink :
     *
     * 1) checks if user has already generated the short link
     * 2) Creates RestTemplate and Sends Post request to "https://api-ssl.bitly.com/v4/shorten" with Authorization Headers to generate the shortlink
     * :: NOTE : because we cant generate the short link for localhost, right now I generate the shortlink for my personal github account and do nothing with the response.
     * 3) Saves User to DB without password
     * 4) Generated unique random UUID and saves UUIDCheck to DB with Valid value of 1 (which means YES ) , userId and UUIDType of REGISTER
     * 5) sends email of Confirming Registration Title and link to the Confirm page with the unique UUID (Which we will use in the confirm method to get the user from db
     * **/
    @Override
    @Transactional
    public void generateRegisterShortLink(RegisterUserBean registerUserBean) {

        EcommerceUser ecommerceUser = userRepository.searchByEmailAndEmptyPassword(registerUserBean.getEmail());
        if (ecommerceUser != null) {
            throw new GeneralException(BogError.SHORT_LINK_IS_ALREADY_SENT_TO_YOUR_EMAIL);
        } else {
            ecommerceUser = new EcommerceUser();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer 6e43cb182d7a93e0daa81166568f830af7d287e9");
        HttpEntity<ShortLinkRequest> request = new HttpEntity<>(new ShortLinkRequest("bit.ly", "http://github.com/shelo16"), headers);

        ResponseEntity<ShortLinkResponse> response = restTemplate
                .exchange(shortLinkUrl, HttpMethod.POST, request, ShortLinkResponse.class);

        BeanUtils.copyProperties(registerUserBean, ecommerceUser);
        ecommerceUser.setBalance(new BigDecimal(0));
        userRepository.save(ecommerceUser);
        UUID uuid = UUID.randomUUID();
        UUIDCheck uuidCheck = new UUIDCheck(ecommerceUser.getId(), uuid.toString(), 1L, UuidTypeEnum.REGISTER);
        uuidRepository.save(uuidCheck);

        emailService.sendSimpleMessage(registerUserBean.getEmail(), "Confirm Registration", "http://localhost:4200/confirm?hash=" + uuid);

    }

    /**
     *
     * ConfirmAuth method : Used both for Register Confirmation and ResetPassword Confirmation
     *
     * 1) Gets the uuid from RequestBody and gets the UuidCheck from DB with Valid value of 1
     * 2) INVALIDATES THE UUID and flushed the change to DB ( This is so another user won't be able to open the page with same UUID )
     * 3) Finds the user with UUIDCheck.userId
     * 4) updates/sets the Password of the user.
     * **/
    @Override
    @Transactional
    public void confirmAuth(AuthBean authBean) {
        UUIDCheck uuidCheck = uuidRepository.getUUIDCheckByUUIDAndByType(authBean.getUuid(), authBean.getType());
        if (uuidCheck == null) {
            throw new GeneralException(BogError.INVALID_REQUEST);
        }
        uuidCheck.setIsValid(0L);
        uuidRepository.saveAndFlush(uuidCheck);

        EcommerceUser ecommerceUser = userRepository.findById(uuidCheck.getUserId())
                .orElseThrow(() -> new GeneralException(BogError.COULDNT_FIND_USER_BY_PROVIDED_ID));
        ecommerceUser.setBcryptedPassword(bCryptPasswordEncoder.encode(authBean.getPassword()));
        userRepository.save(ecommerceUser);

    }

    /**
     *
     * GenerateResetShortLink :
     *
     * 1) checks if user exists
     * 2) Creates RestTemplate and Sends Post request to "https://api-ssl.bitly.com/v4/shorten" with Authorization Headers to generate the shortlink
     * :: NOTE : because we cant generate the short link for localhost, right now I generate the shortlink for my personal github account and do nothing with the response.
     * 3) Generated unique random UUID and saves UUIDCheck to DB with Valid value of 1 (which means YES ) , userId and UUIDType of RESET
     * 4) sends email of Confirming Registration Title and link to the Confirm page with the unique UUID (Which we will use in the confirm method to get the user from db
     * **/
    @Override
    public void generateResetShortLink(ResetPasswordBean resetPasswordBean) {
        EcommerceUser ecommerceUser = userRepository.searchByEmail(resetPasswordBean.getEmail());
        if (ecommerceUser == null) {
            throw new GeneralException(BogError.COULDNT_FIND_USER_BY_PROVIDED_EMAIL);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer 6e43cb182d7a93e0daa81166568f830af7d287e9");
        HttpEntity<ShortLinkRequest> request = new HttpEntity<>(new ShortLinkRequest("bit.ly", "http://github.com/shelo16"), headers);

        ResponseEntity<ShortLinkResponse> response = restTemplate
                .exchange(shortLinkUrl, HttpMethod.POST, request, ShortLinkResponse.class);

        UUID uuid = UUID.randomUUID();
        UUIDCheck uuidCheck = new UUIDCheck(ecommerceUser.getId(), uuid.toString(), 1L, UuidTypeEnum.RESET);
        uuidRepository.save(uuidCheck);

        emailService.sendSimpleMessage(resetPasswordBean.getEmail(), "Reset Your Password", "http://localhost:4200/reset?hash=" + uuid);
    }

}
