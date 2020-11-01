package com.tornikeshelia.bogecommerce.service.products;

import com.tornikeshelia.bogecommerce.model.bean.products.ProductsBean;
import com.tornikeshelia.bogecommerce.model.bean.products.ProductsPurchaseBean;

import java.text.ParseException;
import java.util.List;

public interface ProductsService {

    Long saveProduct(ProductsBean productsBean) throws ParseException;

    ProductsBean getById(Long id);

    List<ProductsBean> getAll();

    List<ProductsBean> getByUserId(Long userId);

    void purchaseProduct(ProductsPurchaseBean productsPurchaseBean);
}
