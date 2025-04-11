package org.example;

import jakarta.validation.Valid;
import org.example.validators.exeption.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<ExpenseDTO> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable Long id) {
        if(id > 1000){
            throw new CustomException("INVALID_ID", "invalid id");
        }
        return expenseService.getExpenseById(id);
    }

    @PostMapping
    public ExpenseDTO saveExpense(@RequestBody @Valid ExpenseDTO expense) {
        return expenseService.saveExpense(expense);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @PutMapping("/{id}")
    public ExpenseDTO updateExpense(@PathVariable Long id, @RequestBody @Valid ExpenseDTO updatedExpense) {
        return expenseService.updateExpense(id, updatedExpense);
    }
}
