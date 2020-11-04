package com.tornikeshelia.bogecommerce.controller;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsGetBean;
import com.tornikeshelia.bogecommerce.service.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin(origins = "*")
@Validated
public class ProductsController {

    @Autowired
    private ProductsService productsService;


    @GetMapping("/{productId}")
    public ProductsGetBean getById(@Valid @PathVariable @Min(1) @NotNull Long productId) {
        return productsService.getById(productId);
    }

    @GetMapping
    public List<ProductsGetBean> getAll() {
        return productsService.getAll();
    }


}
