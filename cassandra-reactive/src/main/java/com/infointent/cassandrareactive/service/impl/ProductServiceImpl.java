package com.infointent.cassandrareactive.service.impl;

import com.infointent.cassandrareactive.entity.Product;
import com.infointent.cassandrareactive.repository.ProductRepository;
import com.infointent.cassandrareactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Override
    public Mono<Product> saveProduct(Product product) {
        product.setId(UUID.randomUUID());
        Mono<Product>  result = productRepo.save(product);
        return result;
    }

    @Override
    public Mono<Product> getProductByName(String productName) {
        Mono<Product> monoProduct = productRepo.findByName(productName);
        return monoProduct;
    }

    @Override
    public Flux<Product> getProductByPrice(double productPrice, String condition) {
        // condition:-  gt=greater than, lt=less than
        Flux<Product> products;
        if ("gt".equals(condition)) {
            products = productRepo.findByPriceGreaterThan(productPrice);
        } else if ("lt".equals(condition)) {
            products = productRepo.findByPriceLessThan(productPrice);
        } else {
            products = productRepo.findByPrice(productPrice);
        }
        return products;
    }
}
