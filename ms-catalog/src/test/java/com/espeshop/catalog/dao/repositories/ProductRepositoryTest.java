package com.espeshop.catalog.dao.repositories;

import com.espeshop.catalog.model.dtos.ProductDTO;
import com.espeshop.catalog.model.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16.1");

    @Test
    void connectionTest() {
        assertTrue(postgreSQLContainer.isCreated());
        assertTrue(postgreSQLContainer.isRunning());
    }

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldFindProductByName() {
        // given
        String productName = "Product 1";

        // when
        var product =productRepository.findByName(productName);
        // then
        assertTrue(product.isPresent());
    }

    @Test
    void shouldNotFindProductByName() {
        // given
        String productName = "Product 101";

        // when
        var product =productRepository.findByName(productName);
        // then
        assertTrue(product.isEmpty());
    }

//    @Test
//    void shouldFindByCode() {
//        // given
//        String skuCode = "SKU-1";
//        ProductDTO filterDTO = new ProductDTO(
//                null,                  // name
//                skuCode,               // SKU code
//                null,                  // min price
//                null,                  // max price
//                null,                  // orderDate since
//                null,                  // orderDate until
//                null,                  // deliveryDate since
//                null,                  // deliveryDate until
//                null,
//                0,                     // page
//                10                     // size
//        );
//
//        // when
//        List<ProductDTO> products = productRepository.findByFilter(filterDTO);
//
//        // then
//        assertEquals(1, products.size());  // Assuming we expect exactly 1 product with SKU-1
//        assertEquals(skuCode, products.get(0).skuCode());
//    }
}








