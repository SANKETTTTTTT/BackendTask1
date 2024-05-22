package com.techBit.springBootBackend;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;

//DataInitializationService.java
@Service
public class DataInitializationService {
 @Autowired
 private ProductTransactionRepository repository;

 private static final String API_URL = "https://s3.amazonaws.com/roxiler.com/product_transaction.json";

 @Transactional
 public void initializeDatabase() throws IOException {
     RestTemplate restTemplate = new RestTemplate();
     ResponseEntity<ProductTransaction[]> response = restTemplate.getForEntity(API_URL, ProductTransaction[].class);
     ProductTransaction[] transactions = response.getBody();

     if (transactions != null) {
         repository.saveAll(Arrays.asList(transactions));
     }
 }
}
