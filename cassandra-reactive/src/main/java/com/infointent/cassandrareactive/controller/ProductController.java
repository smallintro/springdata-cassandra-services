package com.infointent.cassandrareactive.controller;

import com.infointent.cassandrareactive.entity.Product;
import com.infointent.cassandrareactive.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Mono<Product> addProduct(@RequestBody Product product) {
        log.info("Received addProduct request with product: {}", product);
        try {
            return productService.saveProduct(product);
        } catch (Exception ex) {
            log.error("Exception during addProduct. {}", ex.getMessage());
            throw new RuntimeException(String.format("Exception during addProduct. {}", ex.getMessage()));
        }
    }

    @GetMapping("/name/{product_name}")
    public Mono<Product> getProductByName(@PathVariable("product_name") String productName) {
        log.info("Received getProductByName request with productName: {}", productName);
        try {
            return productService.getProductByName(productName);
        } catch (Exception ex) {
            log.error("Exception during getProductByName. {}", ex.getMessage());
            throw new RuntimeException(String.format("Exception during getProductByName. {}", ex.getMessage()));
        }
    }

    @GetMapping("/price/{condition}/{product_price}")
    public Flux<Product> getProductByPrice(@PathVariable("product_price") double productPrice, @PathVariable("condition") String condition) {
        log.info("Received getProductByPrice request with productPrice: {} and condition: {}", productPrice, condition);
        try {
            return productService.getProductByPrice(productPrice, condition);
        } catch (Exception ex) {
            log.error("Exception during getProductByPrice. {}", ex.getMessage());
            throw new RuntimeException(String.format("Exception during getProductByPrice. {}", ex.getMessage()));
        }
    }
}
