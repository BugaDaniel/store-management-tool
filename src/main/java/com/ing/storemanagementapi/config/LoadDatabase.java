package com.ing.storemanagementapi.config;

import com.ing.storemanagementapi.model.Product;
import com.ing.storemanagementapi.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase (ProductRepository productRepository) {
        return  args -> {
            log.info("Preloading " + productRepository.save(new Product("1Name", "descr", new BigDecimal("24.22"), 4, "aCateg", "somBrand")));
            log.info("Preloading " + productRepository.save(new Product("2Name", "2descr", new BigDecimal("66.66"), 80, "a2Categ", "som2Brand")));

        };
    }
}
