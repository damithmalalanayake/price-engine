package com.example.priceengine.controller;

import com.example.priceengine.config.APIConst;
import com.example.priceengine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin("*")
@RestController
@RequestMapping(APIConst.PRODUCT)
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = APIConst.LIST)
    public ResponseEntity<?> listAllProducts() {
        try {
            return ResponseEntity.ok(productService.listProducts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(APIConst.PRICE + "/{productId}/{quantity}")
    public ResponseEntity<?> calculatePrice(@PathVariable Long productId, @PathVariable BigDecimal quantity) {
        try {
            return ResponseEntity.ok(productService.calculatePrice(productId, quantity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
