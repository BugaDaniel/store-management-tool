package com.ing.storemanagementapi.mocks.model;

import com.ing.storemanagementapi.model.Product;

import java.lang.reflect.Field;
import java.math.BigDecimal;


public class ProductMockUtil {

    public static Product createProduct(String name, String priceValue, int quantity){
        return new Product(name, "descr", new BigDecimal(priceValue), quantity, "aCateg", "somBrand");
    }

    // because Product does not have a setter for id, reflection is needed to set it for tests
    public static void setProductId(Product product, Long id) {
        try {
            Field field = Product.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(product, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
