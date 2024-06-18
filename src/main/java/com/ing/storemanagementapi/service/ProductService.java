package com.ing.storemanagementapi.service;

import com.ing.storemanagementapi.exception.ProductInsertionViolationException;
import com.ing.storemanagementapi.exception.ProductNotFoundException;
import com.ing.storemanagementapi.exception.ProductQuantityException;
import com.ing.storemanagementapi.model.Product;
import com.ing.storemanagementapi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    protected static String PRODUCT_NOT_FOUND = "Product not found with id: ";
    protected static String INSERT_WITH_ID_VIOLATION = "Can't insert a new product if it already has an id";
    protected static String NEGATIVE_QUANTITY = "Product can't have a quantity lower than 0";
    
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return findProductById(id);
    }

    public Product insertProduct(Product product) {
        if (product.getId() != null) {
            logger.error("Attempted to insert a product with an existing id: {}", product.getId());
            throw new ProductInsertionViolationException(INSERT_WITH_ID_VIOLATION);
        }

        logger.info("Inserting new product: {}", product);
        return productRepository.save(product);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = findProductById(id);
        updateAllProductFields(product, updatedProduct);
        logger.info("Updating product with id: {}", id);
        return productRepository.save(product);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Product changeProductQuantity(Long id, int newQuantity) {
        logger.info("Changing quantity for product with id: {}", id);
        Product product = findProductById(id);
        int productQuantity = product.getQuantity();
        productQuantity += newQuantity;
        logger.debug("Product quantity after increment or decrement: {}",productQuantity);
        if (productQuantity < 0) {
            throw new ProductQuantityException(NEGATIVE_QUANTITY);
        }

        product.setQuantity(productQuantity);
        return productRepository.save(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        logger.info("Deleting product with id: {}", id);
        findProductById(id);
        productRepository.deleteById(id);
        logger.info("Product with id: {} deleted", id);
    }

    private Product findProductById(Long id) {
        logger.debug("Finding product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(PRODUCT_NOT_FOUND + id);
                    return new ProductNotFoundException(PRODUCT_NOT_FOUND + id);
                });
    }

    private void updateAllProductFields(Product fetchedProduct, Product updatedProduct) {
        fetchedProduct.setName(updatedProduct.getName());
        fetchedProduct.setDescription(updatedProduct.getDescription());
        fetchedProduct.setPrice(updatedProduct.getPrice());
        fetchedProduct.setQuantity(updatedProduct.getQuantity());
        fetchedProduct.setCategory(updatedProduct.getCategory());
        fetchedProduct.setBrand(updatedProduct.getBrand());
        logger.debug("Product fields updated for product: {}", fetchedProduct);
    }
}
