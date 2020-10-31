package com.tornikeshelia.bogecommerce.controller;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsSaveBean;
import com.tornikeshelia.bogecommerce.service.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin(origins = "*")
@Validated
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping(method = RequestMethod.POST)
    public Long saveProduct(@Valid @RequestBody ProductsSaveBean productsSaveBean){
        return productsService.saveProduct(productsSaveBean);
    }

}
