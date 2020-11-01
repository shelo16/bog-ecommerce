package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;

import java.text.ParseException;

public interface ProductsService {

    Long saveProduct(ProductsBean productsBean) throws ParseException;

    ProductsBean getById(Long id);
}
