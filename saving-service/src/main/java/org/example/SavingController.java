package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/savings")
public class SavingController {
    @Autowired
    private SavingService savingService;

    @GetMapping("/balances")
    public ResponseEntity<Map<String, BigDecimal>> getAllSavingsBalance() {
        Map<String, BigDecimal> balances = savingService.getAllSavingsBalance();
        return ResponseEntity.ok(balances);
    }
}
