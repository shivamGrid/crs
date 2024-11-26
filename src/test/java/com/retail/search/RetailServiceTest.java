package com.retail.search;

import com.retail.search.model.ProductCatalogDTO;
import com.retail.search.service.RetailService;

import java.util.Arrays;

public class RetailServiceTest {
    public static void main(String[] args) {
        RetailService retailService = new RetailService();

        ProductCatalogDTO googleTshirt = new ProductCatalogDTO();
        googleTshirt.setId("google-tshirt");
        googleTshirt.setName("Google T-shirt");
        googleTshirt.setGtin("1");
        googleTshirt.setTitle("Official Google T-shirt");
        googleTshirt.setDescription("Comfortable and stylish Google branded T-shirt.");
        googleTshirt.setCategories(Arrays.asList("Apparel", "T-shirts"));
        googleTshirt.setBrands(Arrays.asList("Google"));
        googleTshirt.setLanguageCode("en-US");
        googleTshirt.setAvailability("IN_STOCK");
        googleTshirt.setUri("http://example.com/google-tshirt");
      //  googleTshirt.setPriceInfo(new PriceInfoDTO("19.99", "USD", "29.99"));

        //retailService.uploadProductCatalog(googleTshirt);

// Create and upload the Tommy T-shirt product
        ProductCatalogDTO tommyTshirt = new ProductCatalogDTO();
        tommyTshirt.setId("tommy-tshirt");
        tommyTshirt.setName("Tommy T-shirt");
        tommyTshirt.setGtin("2");
        tommyTshirt.setTitle("Tommy Hilfiger T-shirt");
        tommyTshirt.setDescription("Stylish Tommy Hilfiger branded T-shirt.");
        tommyTshirt.setCategories(Arrays.asList("Apparel", "T-shirts"));
        tommyTshirt.setBrands(Arrays.asList("Tommy Hilfiger"));
        tommyTshirt.setLanguageCode("en-US");
        tommyTshirt.setAvailability("IN_STOCK");
        tommyTshirt.setUri("http://example.com/tommy-tshirt");
//        tommyTshirt.setPriceInfo(new PriceInfoDTO("29.99", "USD", "39.99"));
//        retailService.uploadProductCatalog(tommyTshirt);

// Set the condition to boost products with the brand "Google"
        String condition = "brands: ANY(\"google\")";
        float boostStrength = 1;

        retailService.searchProductsWithBoostSpec("Sweatshirt", 10, condition, boostStrength);

    }
}
