package com.infointent.cassandrareactive;

import com.infointent.cassandrareactive.entity.Product;
import com.infointent.cassandrareactive.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
class CassandraReactiveApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void test_add_and_find() {
        {
            final Product product101 = new Product(UUID.randomUUID(), "product101", 19999.99, 100);
            final Product product102 = new Product(UUID.randomUUID(), "product102", 29999.99, 200);
            final Product product103 = new Product(UUID.randomUUID(), "product103", 39999.99, 300);
            final Product product104 = new Product(UUID.randomUUID(), "product104", 49999.99, 400);

            productRepository.insert(List.of(product101, product102, product103, product104)).subscribe();

            log.info("starting findAll............................................");
            productRepository
                    .findAll()
                    .log()
                    .map(Product::getName)
                    .subscribe(product -> System.out.println("findAll: " + product));

            log.info("starting findByName..........................................");
            productRepository
                    .findByName("product101")
                    .log()
                    .map(Product::getName)
                    .subscribe(product -> System.out.println("findByName: " + product));

            log.info("starting findByPriceGreaterThan................................");
            productRepository
                    .findByPriceGreaterThan(30000)
                    .log()
                    .map(Product::getName)
                    .subscribe(product -> System.out.println("findByPriceGreaterThan: " + product));
        }
    }

}
