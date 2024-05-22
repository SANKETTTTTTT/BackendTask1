package com.techBit.springBootBackend;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//ProductTransactionController.java
@RestController
@RequestMapping("/api/transactions")
public class ProductTransactionController {
 @Autowired
 private ProductTransactionRepository repository;

 @GetMapping
 public Page<ProductTransaction> getTransactions(
         @RequestParam(required = false) String search,
         @RequestParam(defaultValue = "1") int page,
         @RequestParam(defaultValue = "10") int size,
         @RequestParam String month) {
     
     Pageable pageable = PageRequest.of(page - 1, size);
     if (search != null) {
         return repository.findByDateOfSaleContainingAndTitleContaining(month, search, pageable);
     } else {
         return repository.findByDateOfSaleContaining(month, pageable);
     }
 }

 @GetMapping("/statistics")
 public Map<String, Object> getStatistics(@RequestParam String month) {
     List<ProductTransaction> transactions = repository.findByDateOfSaleContaining(month);

     double totalSaleAmount = transactions.stream().filter(ProductTransaction::isSold) .mapToDouble(ProductTransaction::getPrice).sum();

     long totalSoldItems = transactions.stream().filter(ProductTransaction::isSold).count();

     long totalNotSoldItems = transactions.stream().filter(t ->!t.isSold()).count();

     Map<String, Object> stats = new HashMap<>();
     stats.put("totalSaleAmount", totalSaleAmount);
     stats.put("totalSoldItems", totalSoldItems);
     stats.put("totalNotSoldItems", totalNotSoldItems);

     return stats;
 }

 @GetMapping("/bar-chart")
 public Map<String, Long> getBarChart(@RequestParam String month) {
     List<ProductTransaction> transactions = repository.findByDateOfSaleContaining(month);

     Map<String, Long> priceRangeCount = new TreeMap<>();
     priceRangeCount.put("0-100", transactions.stream().filter(t -> t.getPrice() >= 0 && t.getPrice() <= 100).count());
     priceRangeCount.put("101-200", transactions.stream().filter(t -> t.getPrice() >= 101 && t.getPrice() <= 200).count());
     priceRangeCount.put("201-300", transactions.stream().filter(t -> t.getPrice() >= 201 && t.getPrice() <= 300).count());
     priceRangeCount.put("301-400", transactions.stream().filter(t -> t.getPrice() >= 301 && t.getPrice() <= 400).count());
     priceRangeCount.put("401-500", transactions.stream().filter(t -> t.getPrice() >= 401 && t.getPrice() <= 500).count());
     priceRangeCount.put("501-600", transactions.stream().filter(t -> t.getPrice() >= 501 && t.getPrice() <= 600).count());
     priceRangeCount.put("601-700", transactions.stream().filter(t -> t.getPrice() >= 601 && t.getPrice() <= 700).count());
     priceRangeCount.put("701-800", transactions.stream().filter(t -> t.getPrice() >= 701 && t.getPrice() <= 800).count());
     priceRangeCount.put("801-900", transactions.stream().filter(t -> t.getPrice() >= 801 && t.getPrice() <= 900).count());
     priceRangeCount.put("901-above", transactions.stream().filter(t -> t.getPrice() > 900).count());

     return priceRangeCount;
 }

 @GetMapping("/pie-chart")
 public Map<String, Long> getPieChart(@RequestParam String month) {
     List<ProductTransaction> transactions = repository.findByDateOfSaleContaining(month);

     return transactions.stream()
             .collect(Collectors.groupingBy(ProductTransaction::getCategory, Collectors.counting()));
 }

 @GetMapping("/combined-response")
 public Map<String, Object> getCombinedResponse(@RequestParam String month) {
     Map<String, Object> combinedResponse = new HashMap<>();
     combinedResponse.put("transactions", getTransactions(null, 1, 10, month));
     combinedResponse.put("statistics", getStatistics(month));
     combinedResponse.put("barChart", getBarChart(month));
     combinedResponse.put("pieChart", getPieChart(month));
     return combinedResponse;
 }
}
