package com.tornikeshelia.bogecommerce.security.model.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmUserBean {

    @NotNull
    @Min(1)
    private Long userId;

    @NotNull
    private String password;

}
