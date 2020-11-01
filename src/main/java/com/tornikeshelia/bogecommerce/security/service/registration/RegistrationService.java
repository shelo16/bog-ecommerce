package com.tornikeshelia.bogecommerce.security.service.registration;

import com.tornikeshelia.bogecommerce.security.model.register.ConfirmUserBean;
import com.tornikeshelia.bogecommerce.security.model.register.RegisterUserBean;

public interface RegistrationService {

    void generateShortLink(RegisterUserBean registerUserBean);

    void confirmRegistration(ConfirmUserBean confirmUserBean);
}
