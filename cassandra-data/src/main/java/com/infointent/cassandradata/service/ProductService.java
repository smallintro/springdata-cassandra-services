package com.infointent.cassandradata.service;

import com.infointent.cassandradata.entity.ProductReq;
import com.infointent.cassandradata.entity.ProductResp;

public interface ProductService {
    ProductResp saveProduct(ProductReq productReq);

    ProductResp updateProduct(String productId, ProductReq productReq);

    ProductResp deleteProduct(String productId);

    ProductResp getProductByName(String productName);

    ProductResp getProductByPrice(double productPrice, String condition);
}
