package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomeDTO {

    private Long id;
    private String source;
    private String currency;
    private BigDecimal amount;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public IncomeDTO() {}

    public IncomeDTO(Long id, String source, String currency, BigDecimal amount, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.source = source;
        this.currency = currency;
        this.amount = amount;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }


    // Getters and setters
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
