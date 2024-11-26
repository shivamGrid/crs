package com.retail.search.controller;

import com.google.cloud.retail.v2.SearchRequest;
import com.google.cloud.retail.v2.SearchResponse;
import com.google.cloud.retail.v2.SearchServiceClient;
import com.retail.search.model.ProductCatalogDTO;
import com.retail.search.service.RetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private RetailService retailService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadProductCatalogFromFile() {
        String filePath = "/Users/schaudhary/Downloads/search/src/main/resources/test1.json";
        try {
            String fileContent = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);

            // Convert file content to ProductCatalogDTO
            ProductCatalogDTO productCatalogDTO = retailService.parseProductCatalog(fileContent);

            // Upload the product catalog
            retailService.uploadProductCatalog(productCatalogDTO);
            return ResponseEntity.ok("Product catalog uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to read the file.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to upload product catalog.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchProductsWithBoost(
            @RequestParam String query,
            @RequestParam int pageSize,
            @RequestParam String condition,
            @RequestParam float boostStrength) {
        try {
            // Perform the search with boost specification
            retailService.searchProductsWithBoostSpec(query, pageSize, condition, boostStrength);
            return ResponseEntity.ok("Search completed successfully. Check logs for results.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to perform search.");
        }
    }

    @GetMapping
    public List<String> getAllProducts() {
        List<String> products = null;
        try (SearchServiceClient searchClient = SearchServiceClient.create()) {

            String query = ""; // Empty query to fetch all products
            int pageSize = 100; // You can adjust the page size as needed

            SearchRequest searchRequest = SearchRequest.newBuilder()
                    .setPlacement("projects/hopeful-sound-1010/locations/global/catalogs/default_catalog/placements/default_search")
                    .setBranch("projects/hopeful-sound-1010/locations/global/catalogs/default_catalog/branches/default_branch")
                    .setVisitorId(UUID.randomUUID().toString())
                    .setQuery(query)
                    .setPageSize(pageSize)
                    .build();

            // Execute the search request
            SearchServiceClient.SearchPagedResponse pagedResponse = searchClient.search(searchRequest);
            // Convert Iterable to Stream, collect product details, and print them
            products = StreamSupport.stream(pagedResponse.iterateAll().spliterator(), false)
                    .map(result -> {
                        String productId = result.getId();
                        String productTitle = result.getProduct().getTitle();
                        System.out.println("Product ID: " + productId + ", Title: " + productTitle);
                        return "Product ID: " + productId + ", Title: " + productTitle;
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }

        return products;
    }
}