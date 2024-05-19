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

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return findProductById(id);
    }

    public Product insertProduct(Product product) {
        if (product.getId() != null) throw new ProductInsertionViolationException("can't insert a new product with an already existing id");
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
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
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
