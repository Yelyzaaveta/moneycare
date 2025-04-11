package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<ExpenseDTO> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(ExpenseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ExpenseDTO getExpenseById(Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.map(ExpenseMapper::toDTO).orElse(null);
    }

    public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) {
        Expense expense = ExpenseMapper.toModel(expenseDTO);
        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseMapper.toDTO(savedExpense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public ExpenseDTO updateExpense(Long id, ExpenseDTO updatedExpenseDTO) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);

        if (existingExpense.isPresent()) {
            Expense expense = existingExpense.get();
            expense.setCurrency(updatedExpenseDTO.getCurrency());
            expense.setAmount(updatedExpenseDTO.getAmount());
            expense.setCategory(updatedExpenseDTO.getCategory());
            Expense updatedExpense = expenseRepository.save(expense);
            return ExpenseMapper.toDTO(updatedExpense);
        }

        return null;
    }
}
