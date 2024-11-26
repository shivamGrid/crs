package com.retail.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.retail.v2.*;
import com.retail.search.model.ProductCatalogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

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
                .setDescription(productCatalogDTO.getDescription())
                .setGtin(productCatalogDTO.getGtin())
                .addAllCategories(productCatalogDTO.getCategories())
                .addAllBrands(productCatalogDTO.getBrands())
                .setLanguageCode(productCatalogDTO.getLanguageCode())
                .setAvailability(Product.Availability.valueOf(productCatalogDTO.getAvailability()))
                .setUri(productCatalogDTO.getUri())
//                .setPriceInfo(Product.PriceInfo.newBuilder()
//                        .setPrice(Double.parseDouble(productCatalogDTO.getPriceInfo().getPrice()))
//                        .setCurrencyCode(productCatalogDTO.getPriceInfo().getCurrencyCode())
//                        .setOriginalPrice(Double.parseDouble(productCatalogDTO.getPriceInfo().getOriginalPrice()))
                .build();
    }
    public void searchProductsWithBoostSpec(String query, int pageSize, String boostCondition, float boostStrength) {
        SearchRequest.BoostSpec boostSpec = SearchRequest.BoostSpec.newBuilder()
                .addConditionBoostSpecs(SearchRequest.BoostSpec.ConditionBoostSpec.newBuilder()
                        .setCondition(boostCondition)
                        .setBoost(boostStrength)
                        .build())
                .build();

        SearchRequest searchRequest = SearchRequest.newBuilder()
                .setPlacement("projects/hopeful-sound-1010/locations/global/catalogs/default_catalog/placements/default_search")
                .setBranch("projects/hopeful-sound-1010/locations/global/catalogs/default_catalog/branches/default_branch")
                .setVisitorId(UUID.randomUUID().toString())
                .setQuery(query)
                .setPageSize(pageSize)
                .setBoostSpec(boostSpec)
//                .addFacetSpecs(SearchRequest.FacetSpec.newBuilder()
//                        .setFacetKey(SearchRequest.FacetSpec.FacetKey.newBuilder()
//                                .setKey("IN_STOCK")
//                                .build())
//                        .setLimit(10)
                .build();

        try (SearchServiceClient searchClient = SearchServiceClient.create()) {
            SearchServiceClient.SearchPagedResponse pagedResponse = searchClient.search(searchRequest);
            ArrayList<String> results = new ArrayList<>();
            for (SearchResponse.SearchResult result : pagedResponse.iterateAll()) {
                results.add(String.valueOf(result));
            }
            System.out.println("result: ");
            for(int i =0;i<results.size();i++){
                System.out.println(results.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProductCatalogDTO parseProductCatalog(String fileContent) throws IOException {
        return objectMapper.readValue(fileContent, ProductCatalogDTO.class);
    }
}
