package com.techBit.springBootBackend;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//DataInitializationController.java
@RestController
@RequestMapping("/api")
public class DataInitializationController {
 @Autowired
 private DataInitializationService service;

 @GetMapping("/initialize")
 public ResponseEntity<String> initializeDatabase() {
     try {
         service.initializeDatabase();
         return ResponseEntity.ok("Database initialized successfully");
     } catch (IOException e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to initialize database");
     }
 }
}
