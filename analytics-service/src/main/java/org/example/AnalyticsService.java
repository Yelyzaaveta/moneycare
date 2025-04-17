package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private RestTemplate restTemplate;

    private final String EXPENSE_SERVICE_URL = "http://localhost:8088/expenses";
    private final String INCOME_SERVICE_URL = "http://localhost:8087/incomes";

    public BigDecimal calculateBalance() {
        List<IncomeDTO> incomes = getAllIncomes();
        List<ExpenseDTO> expenses = getAllExpenses();

        BigDecimal totalIncome = incomes.stream()
                .map(IncomeDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = expenses.stream()
                .map(ExpenseDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalIncome.subtract(totalExpense);
    }

    public Map<String, BigDecimal> analyzeExpensesByCategory() {
        List<ExpenseDTO> expenses = getAllExpenses();

        return expenses.stream()
                .collect(Collectors.groupingBy(
                        ExpenseDTO::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, ExpenseDTO::getAmount, BigDecimal::add)
                ));
    }

    public Map<String, BigDecimal> analyzeExpensesByCategoryPercentage() {
        List<ExpenseDTO> expenses = getAllExpenses();

        BigDecimal totalExpense = expenses.stream()
                .map(ExpenseDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalExpense.compareTo(BigDecimal.ZERO) == 0) {
            return Map.of();
        }

        return expenses.stream()
                .collect(Collectors.groupingBy(
                        ExpenseDTO::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, ExpenseDTO::getAmount, BigDecimal::add)
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .multiply(BigDecimal.valueOf(100))
                                .divide(totalExpense, 1, RoundingMode.HALF_UP)
                ));
    }

    private List<IncomeDTO> getAllIncomes() {
        IncomeDTO[] incomes = restTemplate.getForObject(INCOME_SERVICE_URL, IncomeDTO[].class);
        return incomes != null ? Arrays.asList(incomes) : List.of();
    }

    private List<ExpenseDTO> getAllExpenses() {
        ExpenseDTO[] expenses = restTemplate.getForObject(EXPENSE_SERVICE_URL, ExpenseDTO[].class);
        return expenses != null ? Arrays.asList(expenses) : List.of();
    }
}
