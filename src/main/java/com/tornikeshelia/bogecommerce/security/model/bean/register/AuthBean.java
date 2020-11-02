package com.tornikeshelia.bogecommerce.security.model.bean.register;

import com.tornikeshelia.bogecommerce.security.model.enums.UuidTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthBean {

    @NotNull
    private String uuid;

    @NotNull
    private String password;

    @NotNull
    private UuidTypeEnum type;

}
