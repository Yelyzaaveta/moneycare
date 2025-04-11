package org.example;

public class ExpenseMapper {

    public static ExpenseDTO toDTO(Expense expense) {
        return new ExpenseDTO(expense.getId(), expense.getCategory(), expense.getCurrency(), expense.getAmount());
    }

    public static Expense toModel(ExpenseDTO expenseDTO) {
        return new Expense(expenseDTO.getId(), expenseDTO.getCategory(), expenseDTO.getCurrency(), expenseDTO.getAmount());
    }
}
