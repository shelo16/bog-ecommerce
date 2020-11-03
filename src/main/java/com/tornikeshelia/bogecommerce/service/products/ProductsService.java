package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsGetBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsSaveBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsPurchaseBean;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ProductsService {

    Long saveProduct(ProductsSaveBean productsSaveBean, HttpServletRequest request) throws ParseException, IOException;

    ProductsGetBean getById(Long id);

    List<ProductsGetBean> getAll();

    List<ProductsGetBean> getByUserId(Long userId, HttpServletRequest request);

    void purchaseProduct(ProductsPurchaseBean productsPurchaseBean, HttpServletRequest request);
}
