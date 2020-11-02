package com.tornikeshelia.bogecommerce.security.model.bean.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordBean {

    @NotNull
    private String email;

}
