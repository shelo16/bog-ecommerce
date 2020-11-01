package com.tornikeshelia.bogecommerce.controller;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;
import com.tornikeshelia.bogecommerce.service.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin(origins = "*")
@Validated
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @PostMapping
    public Long saveProduct(@Valid @RequestBody ProductsBean productsBean) throws ParseException {
        return productsService.saveProduct(productsBean);
    }

    @GetMapping("/{id}")
    public ProductsBean getById(@Valid @PathVariable Long id){
        return productsService.getById(id);
    }


}
