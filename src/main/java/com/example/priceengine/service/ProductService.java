package com.example.priceengine.service;

import com.example.priceengine.model.Product;
import com.example.priceengine.model.exchange.ProductExchange;
import com.example.priceengine.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    public static final String ERROR_MESSAGE_NO_PRODUCTS_FOUND = "No products found!";
    public static final String ERROR_MESSAGE_PRODUCT_IS_NOT_VALID = "Product is not valid!";
    public static final double DISCOUNT_VAL = 0.9;
    public static final double COMPENSATE_VAL = 1.3;
    ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<ProductExchange> listProducts() throws Exception {
        List<Product> allProducts = productRepo.findAll();
        if (allProducts.isEmpty()) {
            throw new Exception(ERROR_MESSAGE_NO_PRODUCTS_FOUND);
        }
        return allProducts.parallelStream().map(product -> {
            ProductExchange productExchange = new ProductExchange();
            productExchange.setId(product.getId());
            productExchange.setCartonPrice(product.getCartonPrice());
            productExchange.setCartonSize(product.getCartonSize());
            productExchange.setName(product.getName());
            return productExchange;
        }).collect(Collectors.toList());
    }

    public BigDecimal calculatePrice(Long productId, BigDecimal quantity) throws Exception {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new Exception(ERROR_MESSAGE_PRODUCT_IS_NOT_VALID);
        }
        Product product = optionalProduct.get();
        BigDecimal price = new BigDecimal(0);
        BigDecimal requiredCartons = quantity.divide(BigDecimal.valueOf(product.getCartonSize()), MathContext.DECIMAL128)
                .setScale(0, RoundingMode.HALF_DOWN);
        BigDecimal requiredUnits = quantity.remainder(BigDecimal.valueOf(product.getCartonSize()));
        if (requiredCartons.compareTo(BigDecimal.valueOf(3)) > 0) {
            price = BigDecimal.valueOf(product.getCartonPrice())
                    .setScale(2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(DISCOUNT_VAL))
                    .multiply(requiredCartons);
        } else {
            price = price.add(requiredCartons.multiply(BigDecimal.valueOf(product.getCartonPrice())).setScale(2, RoundingMode.HALF_UP));
        }
        if (requiredUnits.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal priceForUnits = BigDecimal.valueOf(product.getCartonPrice())
                    .setScale(2, RoundingMode.HALF_UP)
                    .divide(BigDecimal.valueOf(product.getCartonSize()), MathContext.DECIMAL128)
                    .multiply(BigDecimal.valueOf(COMPENSATE_VAL))
                    .multiply(requiredUnits);
            price = price.add(priceForUnits);
        }
        return price.setScale(2, RoundingMode.HALF_UP);
    }
}
