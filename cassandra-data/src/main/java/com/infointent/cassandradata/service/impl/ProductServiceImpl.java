package com.infointent.cassandradata.service.impl;

import com.infointent.cassandradata.entity.Product;
import com.infointent.cassandradata.entity.ProductReq;
import com.infointent.cassandradata.entity.ProductResp;
import com.infointent.cassandradata.repository.ProductRepository;
import com.infointent.cassandradata.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Override
    public ProductResp saveProduct(ProductReq productReq) {
        Product product = Product.builder()
                .productId(UUID.randomUUID().toString())
                .productName(productReq.getProductName())
                .productPrice(productReq.getProductPrice())
                .isAvailable(productReq.isAvailable())
                .build();
        productRepo.save(product);
        return new ProductResp("Added successfully", List.of(product));
    }

    @Override
    public ProductResp updateProduct(String productId, ProductReq productReq) {
        if (productRepo.existsById(productId)) {
            Product product = Product.builder()
                    .productId(productId)
                    .productName(productReq.getProductName())
                    .productPrice(productReq.getProductPrice())
                    .isAvailable(productReq.isAvailable())
                    .build();
            productRepo.save(product);
            return new ProductResp("Updated successfully", List.of(product));
        }
        throw new InvalidParameterException("No Data found for productId: " + productId);
    }

    @Override
    public ProductResp deleteProduct(String productId) {
        Optional<Product> productOptional = productRepo.findById(productId);
        if (productOptional.isPresent()) {
            productRepo.deleteById(productId);
            return new ProductResp("Deleted successfully", List.of(productOptional.get()));
        }
        throw new InvalidParameterException("No Data found for productId: " + productId);
    }

    @Override
    public ProductResp getProductByName(String productName) {
        Optional<Product> productOptional = productRepo.findByProductName(productName);
        if (productOptional.isPresent()) {
            return new ProductResp("success", List.of(productOptional.get()));
        }
        return new ProductResp("failed", null);
    }

    @Override
    public ProductResp getProductByPrice(double productPrice, String condition) {
        // condition:-  gt=greater than, lt=less than
        List<Product> products;
        if ("gt".equals(condition)) {
            products = productRepo.findByProductPriceGreaterThan(productPrice);
        } else if ("lt".equals(condition)) {
            products = productRepo.findByProductPriceLessThan(productPrice);
        } else {
            products = productRepo.findByProductPrice(productPrice);
        }
        return new ProductResp("success", products);
    }
}
