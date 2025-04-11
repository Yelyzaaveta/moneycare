package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/balance")
    public BigDecimal getBalance() {
        return analyticsService.calculateBalance();
    }

    @GetMapping("/expenses-category")
    public Map<String, BigDecimal> getExpensesByCategory() {
        return analyticsService.analyzeExpensesByCategory();
    }

    @GetMapping("/expenses-percentage")
    public Map<String, BigDecimal> getExpensesByCategoryPercentage() {
        return analyticsService.analyzeExpensesByCategoryPercentage();
    }
}
