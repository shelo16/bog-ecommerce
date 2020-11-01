package com.tornikeshelia.bogecommerce.controller;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsPurchaseBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;
import com.tornikeshelia.bogecommerce.service.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin(origins = "*")
@Validated
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @PostMapping
    public Long saveProduct(@Valid @RequestBody @NotNull ProductsBean productsBean) throws ParseException {
        return productsService.saveProduct(productsBean);
    }

    @GetMapping("/{productId}")
    public ProductsBean getById(@Valid @PathVariable @Min(1) @NotNull Long productId){
        return productsService.getById(productId);
    }

    @GetMapping
    public List<ProductsBean> getAll(){
        return productsService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<ProductsBean> getAll(@Valid @PathVariable @Min(1) @NotNull Long userId){
        return productsService.getByUserId(userId);
    }

    @PostMapping("/purchase")
    public void purchaseProduct(@Valid @RequestBody @NotNull ProductsPurchaseBean productsPurchaseBean){
        productsService.purchaseProduct(productsPurchaseBean);
    }


}
