package com.infointent.cassandradata.repository;

import com.infointent.cassandradata.entity.Product;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CassandraRepository<Product, String> {

    @AllowFiltering
    Optional<Product> findByProductName(String productName);

    @AllowFiltering
    List<Product> findByProductPriceGreaterThan(double productPrice);

    @AllowFiltering
    List<Product> findByProductPriceLessThan(double productPrice);

    @AllowFiltering
    List<Product> findByProductPrice(double productPrice);
}
