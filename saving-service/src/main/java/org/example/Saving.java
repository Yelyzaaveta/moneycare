package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "savings")
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal targetAmount;

    private BigDecimal currentBalance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "saving", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Deposit> depositHistory = new ArrayList<>();

    public Saving() {}

    public Saving(String name, BigDecimal targetAmount) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentBalance = BigDecimal.ZERO;
    }

    public void addDeposit(BigDecimal amount) {
        this.currentBalance = this.currentBalance.add(amount);
        Deposit deposit = new Deposit(amount);
        deposit.setSaving(this);
        this.depositHistory.add(deposit);
    }

    // Геттери та сеттери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<Deposit> getDepositHistory() {
        return depositHistory;
    }

    public void setDepositHistory(List<Deposit> depositHistory) {
        this.depositHistory = depositHistory;
    }
}
