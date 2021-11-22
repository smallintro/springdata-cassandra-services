package com.infointent.cassandradata;

import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.infointent.cassandradata.entity.Product;
import com.infointent.cassandradata.entity.ProductReq;
import com.infointent.cassandradata.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.cassandra.core.query.Criteria.where;
import static org.springframework.data.cassandra.core.query.Query.query;

@RunWith(SpringRunner.class)
@SpringBootTest
class CassandraDataApplicationTests {

    @Autowired()
    private ProductService productService;

    @Autowired
    private CassandraOperations cassandraTemplate;


    @Test
    void contextLoads() {
    }

    public static ProductReq newProductReq(String name, double price) {
        ProductReq newProductReq = new ProductReq(name, price, name.indexOf("_") > 0 ? false : true);
        System.out.println(newProductReq);
        return newProductReq;
    }

    public static Product newProduct(String name, double price) {
        Product newProduct = new Product(UUID.randomUUID().toString(), name, price, name.indexOf("_") > 0 ? false : true);
        System.out.println(newProduct);
        return newProduct;
    }

    @Test
    public void testSaveProduct() {
        // save produce
        productService.saveProduct(newProductReq("Test101", 101.99));
        // query added product
        Product newProduct = cassandraTemplate.selectOne(query(where("productName").is("Test101")).withAllowFiltering(), Product.class);
        // assert to test
        assertThat(newProduct).isNotNull();
        assertThat(newProduct.getProductName()).isEqualTo("Test101");
        assertThat(newProduct.isAvailable()).isEqualTo(true);
        assertThat(newProduct.getProductPrice()).isEqualTo(101.99);
        // Remove product entry
        cassandraTemplate.deleteById(newProduct.getProductId(), Product.class);
    }

    @Test
    public void testUpdateProduct() {
        // save produce
        cassandraTemplate.insert(newProduct("Test102", 102.99));
        // query added product
        Product newProduct = cassandraTemplate.selectOne(query(where("productName").is("Test102")).withAllowFiltering(), Product.class);
        // Get productId
        assertThat(newProduct).isNotNull();
        String productId = newProduct.getProductId();
        // Update Product
        productService.updateProduct(productId, newProductReq("Test_102", 102));
        // query updated product
        Product updatedProduct = cassandraTemplate.selectOne(query(where("productName").is("Test_102")).withAllowFiltering(), Product.class);
        // assert to test
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getProductId()).isEqualTo(productId);
        assertThat(updatedProduct.isAvailable()).isEqualTo(false);
        // Remove product entry
        cassandraTemplate.deleteById(productId, Product.class);
    }

    @Test
    public void testDeleteProductById() {
        // save produce
        cassandraTemplate.insert(newProduct("Test103", 102.99));
        // query added product
        Product newProduct = cassandraTemplate.selectOne(query(where("productName").is("Test103")).withAllowFiltering(), Product.class);
        // Get productId
        assertThat(newProduct).isNotNull();
        String productId = newProduct.getProductId();
        // Delete the product
        productService.deleteProduct(productId);
        // query deleted product
        Product deletedProduct = cassandraTemplate.selectOneById(productId, Product.class);
        // assert to test
        assertThat(deletedProduct).isEqualTo(null);
    }

    @Test
    public void testGetProductByPrice() {
        // save produce
        productService.saveProduct(newProductReq("Test104", 104.99));
        productService.saveProduct(newProductReq("Test105", 105.99));
        productService.saveProduct(newProductReq("Test106", 106.99));
        productService.saveProduct(newProductReq("Test107", 107.99));
        // query added product by price greater than
        List<Product> products1 = productService.getProductByPrice(105, "gt").getProducts();
        // assert to test
        assertThat(products1).isNotNull();
        assertThat(products1.size()).isEqualTo(3);
        // query added product by price less than
        List<Product> products2 = productService.getProductByPrice(106, "lt").getProducts();
        // assert to test
        assertThat(products2).isNotNull();
        assertThat(products2.size()).isEqualTo(2);
        // query added product by price equal to
        List<Product> products3 = productService.getProductByPrice(106.99, "eq").getProducts();
        Product productsEq = cassandraTemplate.selectOne(
                SimpleStatement.newInstance("SELECT productid, product_name, product_price, is_available FROM t_product WHERE product_price = ? ALLOW FILTERING;", 106.99)
                , Product.class);
        // assert to test
        assertThat(products3).isNotNull();
        assertThat(products3.size()).isEqualTo(1);
        assertThat(products3.get(0).getProductId()).isEqualTo(productsEq.getProductId());
        // query added product by price equal to
        List<Product> products4 = productService.getProductByPrice(106, "eq").getProducts();
        // assert to test
        assertThat(products4).isNotNull();
        assertThat(products4.size()).isEqualTo(0);
        // Remove product entry
        cassandraTemplate.delete(Product.class);
    }

}
