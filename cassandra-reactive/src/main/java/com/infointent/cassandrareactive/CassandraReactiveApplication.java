package com.infointent.cassandrareactive;

import com.infointent.cassandrareactive.entity.Product;
import com.infointent.cassandrareactive.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@Slf4j
public class CassandraReactiveApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(CassandraReactiveApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("adding dummy data for testing reactive apis");
        final Product product101 = new Product(UUID.randomUUID(), "product101", 19999.99, 100);
        final Product product102 = new Product(UUID.randomUUID(), "product102", 29999.99, 200);
        final Product product103 = new Product(UUID.randomUUID(), "product103", 39999.99, 300);
        final Product product104 = new Product(UUID.randomUUID(), "product104", 49999.99, 400);
        productRepository.insert(List.of(product101, product102, product103, product104)).subscribe();
        log.info("added dummy data for testing reactive apis");
    }

}
