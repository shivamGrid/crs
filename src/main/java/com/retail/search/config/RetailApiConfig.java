package com.retail.search.config;

import com.google.cloud.retail.v2.CatalogServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetailApiConfig {
    @Bean
    public CatalogServiceClient catalogServiceClient() throws Exception {
        return CatalogServiceClient.create();
    }
}
