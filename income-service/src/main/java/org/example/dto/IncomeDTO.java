package org.example.dto;

import org.example.validators.CurrencyConstraint;
import org.example.validators.OneWordConstraint;

import java.math.BigDecimal;

public class IncomeDTO {

    private Long id;

    @OneWordConstraint()
    private String source;

    @CurrencyConstraint()
    private String currency;
    private BigDecimal amount;

    public IncomeDTO() {}

    public IncomeDTO(Long id, String source, String currency, BigDecimal amount) {
        this.id = id;
        this.source = source;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
