package com.retail.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.retail.v2.*;
import com.retail.search.model.ProductCatalogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class RetailService {
    @Autowired
    private CatalogServiceClient catalogServiceClient;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void uploadProductCatalog(ProductCatalogDTO productCatalogDTO) {
        Product product = buildProduct(productCatalogDTO);
        // Replace with your actual project, location, catalog, and branch IDs
        BranchName parent = BranchName.of("hopeful-sound-1010", "global", "default_catalog", "default_branch");
        String productId = productCatalogDTO.getId();

        try (ProductServiceClient productServiceClient = ProductServiceClient.create()) {
            Product response = productServiceClient.createProduct(parent, product, productId);
            System.out.println("Product created: " + response.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Product buildProduct(ProductCatalogDTO productCatalogDTO) {
        return Product.newBuilder()
                .setId(productCatalogDTO.getId())
                .setName(productCatalogDTO.getName())
                .setTitle(productCatalogDTO.getTitle())
//                .setDescription(productCatalogDTO.getDescription())
//                .setGtin(productCatalogDTO.getGtin())
                .addAllCategories(productCatalogDTO.getCategories())
//                .addAllBrands(productCatalogDTO.getBrands())
//                .setLanguageCode(productCatalogDTO.getLanguageCode())
//                .setAvailability(Product.Availability.valueOf(productCatalogDTO.getAvailability()))
//                .setUri(productCatalogDTO.getUri())
//                .setPriceInfo(Product.PriceInfo.newBuilder()
//                        .setPrice(Double.parseDouble(productCatalogDTO.getPriceInfo().getPrice()))
//                        .setCurrencyCode(productCatalogDTO.getPriceInfo().getCurrencyCode())
//                        .setOriginalPrice(Double.parseDouble(productCatalogDTO.getPriceInfo().getOriginalPrice()))
//                        .build())
                // Map other fields as necessary
                .build();
    }

    public ProductCatalogDTO parseProductCatalog(String fileContent) throws IOException {
        return objectMapper.readValue(fileContent, ProductCatalogDTO.class);
    }
}
