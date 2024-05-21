package com.ing.storemanagementapi.service;

import com.ing.storemanagementapi.exception.ProductInsertionViolationException;
import com.ing.storemanagementapi.exception.ProductNotFoundException;
import com.ing.storemanagementapi.model.Product;
import com.ing.storemanagementapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    protected static String PRODUCT_NOT_FOUND = "Product not found with id: ";
    protected static String INSERT_WITH_ID_VIOLATION = "Can't insert a new product if it already has an id";

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return findProductById(id);
    }

    public Product insertProduct(Product product) {
        if (product.getId() != null) throw new ProductInsertionViolationException(INSERT_WITH_ID_VIOLATION);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = findProductById(id);
        updateAllProductFields(product, updatedProduct);
        return productRepository.save(product);
    }

    public void delete(Long id) {
        findProductById(id);
        productRepository.deleteById(id);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + id));
    }

    private void updateAllProductFields(Product fetchedProduct, Product updatedProduct) {
        fetchedProduct.setName(updatedProduct.getName());
        fetchedProduct.setDescription(updatedProduct.getDescription());
        fetchedProduct.setPrice(updatedProduct.getPrice());
        fetchedProduct.setQuantity(updatedProduct.getQuantity());
        fetchedProduct.setCategory(updatedProduct.getCategory());
        fetchedProduct.setBrand(updatedProduct.getBrand());
    }
}
