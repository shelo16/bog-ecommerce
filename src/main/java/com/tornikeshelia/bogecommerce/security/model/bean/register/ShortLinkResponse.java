package com.tornikeshelia.bogecommerce.security.model.bean.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkResponse {

    private String link;
    private String long_url;

}
