package com.retail.search.controller;

import com.retail.search.model.ProductCatalogDTO;
import com.retail.search.service.RetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private RetailService retailService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadProductCatalogFromFile() {
        String filePath = "filepath";
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

}