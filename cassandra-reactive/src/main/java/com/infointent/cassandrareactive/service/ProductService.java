package com.infointent.cassandrareactive.service;

import com.infointent.cassandrareactive.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> saveProduct(Product product);

    Mono<Product> getProductByName(String productName);

    Flux<Product> getProductByPrice(double productCount, String condition);
}
