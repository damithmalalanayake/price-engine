package com.example.priceengine.controller;

import com.example.priceengine.PriceEngineApplicationTests;
import com.example.priceengine.config.APIConst;
import com.example.priceengine.service.ProductService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest extends PriceEngineApplicationTests {
    @Test
    public void TEST_calculatedPriceForProduct1AndQuantity10ShouldBeReturnOk() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_PRICE + "/1/10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(113.75));
    }

    @Test
    public void TEST_calculatedPriceForProduct1AndQuantity20ShouldBeReturnOk() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_PRICE + "/1/20")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(175.00));
    }

    @Test
    public void TEST_calculatedPriceForProduct1AndQuantity30ShouldBeReturnOk() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_PRICE + "/1/30")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(288.75));
    }

    @Test
    public void TEST_calculatedPriceForProduct2AndQuantity5ShouldBeReturnOk() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_PRICE + "/2/5")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(825.00));
    }

    @Test
    public void TEST_calculatedPriceForProduct2AndQuantity22ShouldBeReturnOk() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_PRICE + "/2/22")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3399.00));
    }

    @Test
    public void TEST_calculatedPriceForProduct2AndQuantity30ShouldBeReturnOk() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_PRICE + "/2/30")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4455.00));
    }

    @Test
    public void TEST_calculatedPriceForProduct4AndQuantity30ShouldBeReturnBadRequest() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_PRICE + "/4/30")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(ProductService.ERROR_MESSAGE_PRODUCT_IS_NOT_VALID));
    }

    @Test
    public void TEST_ProductListShouldBeReturn() throws Exception {
        this.mock.perform(get(APIConst.FINAL_URL_PRODUCT_LIST)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(IsCollectionWithSize.hasSize(3)));
    }
}
