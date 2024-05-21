package com.ing.storemanagementapi.mocks.repository;

import com.ing.storemanagementapi.mocks.model.ProductMockUtil;
import com.ing.storemanagementapi.model.Product;
import com.ing.storemanagementapi.repository.ProductRepository;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Random;


public class ProductRepositoryMockSetup {

    public static final long availableProductId = 1L;
    private static final Random random = new Random();

    public static void setupProductRepositoryMocks(ProductRepository productRepository) {
        // Mock behaviors for findById
        Mockito.when(productRepository.findById(Mockito.anyLong()))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);
                    // when findById is called, only for id = 1 will return a product
                    if (id.equals(availableProductId)) {
                        Product product = ProductMockUtil.createProduct("1Name", "241.12", 2);
                        ProductMockUtil.setProductId(product, availableProductId);
                        return Optional.of(product);
                    } else {
                        return Optional.empty();
                    }
                });

        // Mock behaviors for findAll
        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(
                        ProductMockUtil.createProduct("1Name1", "241.12", 7),
                        ProductMockUtil.createProduct("2Name2", "123.77", 77)
                ));

        // Mock behaviors for save
        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    // mock behavior for save in db
                    // if id == null means it tries to insert, so it returns a product with id after insert success
                    if (product.getId() == null){
                        ProductMockUtil.setProductId(product, random.nextLong());
                    }
                    // if product already has id it means update is wanted
                    return product;
                });

        // Mock behaviors for deleteById
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyLong());
    }

}
