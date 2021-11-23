package com.infointent.cassandrareactive.repository;

import com.infointent.cassandrareactive.entity.Product;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCassandraRepository<Product, String> {

    @AllowFiltering
    Mono<Product> findByName(String productName);

    @AllowFiltering
    Flux<Product> findByPriceGreaterThan(double productPrice);

    @AllowFiltering
    Flux<Product> findByPriceLessThan(double productPrice);

    @AllowFiltering
    Flux<Product> findByPrice(double productPrice);
}
