package com.tornikeshelia.bogecommerce.security.model.bean.checkuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckUserAuthResponse {

    private Boolean isAuthenticated;
    private String email;

}
