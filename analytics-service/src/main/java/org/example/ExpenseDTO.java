package org.example;

import jakarta.validation.constraints.NotNull;
import org.example.validators.CurrencyConstraint;
import org.example.validators.OneWordConstraint;

import java.math.BigDecimal;

public class ExpenseDTO {

    private Long id;

    @OneWordConstraint()
    @NotNull(message = "Category cannot be null")
    private String category;

    @NotNull(message = "Currency cannot be null")
    @CurrencyConstraint()
    private String currency;
    private BigDecimal amount;

    public ExpenseDTO() {}

    public ExpenseDTO(Long id, String category, String currency, BigDecimal amount) {
        this.id = id;
        this.category = category;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
