package com.tornikeshelia.bogecommerce.security.service.registration;

import com.tornikeshelia.bogecommerce.security.model.bean.register.AuthBean;
import com.tornikeshelia.bogecommerce.security.model.bean.register.RegisterUserBean;
import com.tornikeshelia.bogecommerce.security.model.bean.register.ResetPasswordBean;

public interface RegistrationService {

    void generateRegisterShortLink(RegisterUserBean registerUserBean);

    void generateResetShortLink(ResetPasswordBean resetPasswordBean);

    void confirmAuth(AuthBean authBean);
}
