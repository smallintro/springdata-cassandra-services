package com.infointent.cassandradata.controller;

import com.infointent.cassandradata.entity.ProductReq;
import com.infointent.cassandradata.entity.ProductResp;
import com.infointent.cassandradata.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity addProduct(@RequestBody ProductReq productReq) {
        log.info("Received addProduct request with productReq: {}", productReq);
        try {
            ProductResp response = productService.saveProduct(productReq);
            return new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("Exception during addProduct. {}", ex.getMessage());
            return new ResponseEntity("Operation failed. Exception: " + ex.getMessage(), HttpStatus.OK);
        }
    }

    @PutMapping("/{product_id}")
    public ResponseEntity updateProduct(@PathVariable("product_id") String productId, @RequestBody ProductReq productReq) {
        log.info("Received updateProduct request with productId: {} and productReq: {}", productId, productReq);
        try {
            ProductResp response = productService.updateProduct(productId, productReq);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception during updateProduct. {}", ex.getMessage());
            return new ResponseEntity("Operation failed. Exception: " + ex.getMessage(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity deleteProduct(@PathVariable("product_id") String productId) {
        log.info("Received deleteProduct request with productId: {}", productId);
        try {
            ProductResp response = productService.deleteProduct(productId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception during deleteProduct. {}", ex.getMessage());
            return new ResponseEntity("Operation failed. Exception: " + ex.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/name/{product_name}")
    public ResponseEntity getProductByName(@PathVariable("product_name") String productName) {
        log.info("Received getProductByName request with productName: {}", productName);
        try {
            ProductResp response = productService.getProductByName(productName);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception during getProductByName. {}", ex.getMessage());
            return new ResponseEntity("Operation failed. Exception: " + ex.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/price/{condition}/{product_price}")
    public ResponseEntity getProductByPrice(@PathVariable("product_price") double productPrice, @PathVariable("condition") String condition) {
        log.info("Received getProductByPrice request with productPrice: {} and condition: {}", productPrice, condition);
        try {
            ProductResp response = productService.getProductByPrice(productPrice, condition);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception during getProductByPrice. {}", ex.getMessage());
            return new ResponseEntity("Operation failed. Exception: " + ex.getMessage(), HttpStatus.OK);
        }
    }

}
