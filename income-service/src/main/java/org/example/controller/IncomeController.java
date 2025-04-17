package org.example.controller;

import jakarta.validation.Valid;
import org.example.service.IncomeService;
import org.example.dto.IncomeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
@Validated
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public List<IncomeDTO> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/{id}")
    public IncomeDTO getIncomeById(@PathVariable Long id) {
        return incomeService.getIncomeById(id);
    }

    @PostMapping
    public IncomeDTO saveIncome(@Valid @RequestBody IncomeDTO incomeDTO) {
        incomeService.saveIncome(incomeDTO);
        return incomeDTO;
    }

    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
    }

    @PutMapping("/{id}")
    public IncomeDTO updateIncome(@PathVariable Long id,@Valid @RequestBody IncomeDTO updatedIncome) {
        return incomeService.updateIncome(id, updatedIncome);
    }
}
