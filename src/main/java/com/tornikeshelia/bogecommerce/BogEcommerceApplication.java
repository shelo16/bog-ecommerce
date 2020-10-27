package com.tornikeshelia.bogecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Configuration
public class BogEcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BogEcommerceApplication.class, args);
    }

}
