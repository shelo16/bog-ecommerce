package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsPurchaseBean;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ProductsService {

    Long saveProduct(ProductsBean productsBean, HttpServletRequest request) throws ParseException, IOException;

    ProductsBean getById(Long id);

    List<ProductsBean> getAll();

    List<ProductsBean> getByUserId(Long userId, HttpServletRequest request);

    void purchaseProduct(ProductsPurchaseBean productsPurchaseBean, HttpServletRequest request);
}
