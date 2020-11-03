package com.tornikeshelia.bogecommerce.controller.authenticated;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsPurchaseBean;
import com.tornikeshelia.bogecommerce.service.products.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/secure/product")
public class AuthenticatedProductController {

    @Autowired
    private ProductsService productsService;

    @PostMapping
    public Long saveProduct(@Valid @RequestBody @NotNull ProductsBean productsBean, HttpServletRequest request) throws ParseException, IOException {
        return productsService.saveProduct(productsBean, request);
    }

    @GetMapping("/user/{userId}")
    public List<ProductsBean> getAllForUser(@Valid @PathVariable @Min(1) @NotNull Long userId, HttpServletRequest request) {
        return productsService.getByUserId(userId, request);
    }

    @PostMapping("/purchase")
    public void purchaseProduct(@Valid @RequestBody @NotNull ProductsPurchaseBean productsPurchaseBean, HttpServletRequest request) {
        productsService.purchaseProduct(productsPurchaseBean, request);
    }

}
