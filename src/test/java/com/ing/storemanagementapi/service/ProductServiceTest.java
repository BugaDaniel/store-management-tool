package com.ing.storemanagementapi.service;

import com.ing.storemanagementapi.exception.ProductNotFoundException;
import com.ing.storemanagementapi.mocks.model.ProductMockUtil;
import com.ing.storemanagementapi.mocks.repository.ProductRepositoryMockSetup;
import com.ing.storemanagementapi.model.Product;
import com.ing.storemanagementapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.ing.storemanagementapi.service.ProductService.PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        ProductRepositoryMockSetup.mockFindAll(productRepository);

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        ProductRepositoryMockSetup.mockFindById(productRepository);

        Product foundProduct = productService.getProductById(ProductRepositoryMockSetup.availableProductId);

        assertNotNull(foundProduct);
        assertEquals(ProductRepositoryMockSetup.availableProductId, foundProduct.getId());
        verify(productRepository, times(1)).findById(ProductRepositoryMockSetup.availableProductId);
    }

    @Test
    void testGetProductById_NotFound() {
        ProductRepositoryMockSetup.mockFindById(productRepository);

        long id = 999L;
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));

        String expectedMessage = PRODUCT_NOT_FOUND + id;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testInsertProduct() {
        ProductRepositoryMockSetup.mockSave(productRepository);

        Product product = ProductMockUtil.createProduct("1Name", "241.12", 2);
        Product savedProduct = productService.insertProduct(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals("1Name", savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct() {
        ProductRepositoryMockSetup.mockSave(productRepository);
        ProductRepositoryMockSetup.mockFindById(productRepository);

        String name = "updName";
        String price = "77.42";
        int quantity = 33;
        Product productWithUpdatedFields = ProductMockUtil.createProduct(name, price, quantity);
        Product updatedProduct = productService.updateProduct(ProductRepositoryMockSetup.availableProductId, productWithUpdatedFields);

        assertNotNull(updatedProduct);
        assertEquals(name, updatedProduct.getName());
        assertEquals(new BigDecimal(price), updatedProduct.getPrice());
        assertEquals(quantity, updatedProduct.getQuantity());
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void testDeleteProductById() {
        ProductRepositoryMockSetup.mockFindById(productRepository);
        ProductRepositoryMockSetup.mockDelete(productRepository);

        productService.delete(ProductRepositoryMockSetup.availableProductId);

        verify(productRepository, times(1)).deleteById(ProductRepositoryMockSetup.availableProductId);
    }

    @Test
    void testDeleteProductById_NotFound() {
        ProductRepositoryMockSetup.mockFindById(productRepository);

        long id = 8899L;
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.delete(id);
        });

        String expectedMessage = PRODUCT_NOT_FOUND + id;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepository, times(0)).deleteById(id);
    }
}