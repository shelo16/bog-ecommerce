package com.tornikeshelia.bogecommerce.security.model.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserBean {

    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String iban;
    private String personalNumber;

}
