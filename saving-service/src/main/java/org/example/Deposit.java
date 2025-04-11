package org.example;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Додаємо унікальний ідентифікатор для депозиту

    private LocalDate date;
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saving_id")  // колонка для зв'язку в таблиці deposits
    private Saving saving;

    public Deposit() {
        this.date = LocalDate.now();
    }

    public Deposit(BigDecimal amount) {
        this.date = LocalDate.now();
        this.amount = amount;
    }

    // Геттери та сеттери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getDepositAmount() {
        return amount;
    }

    public void setDepositAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Saving getSaving() {
        return saving;
    }

    public void setSaving(Saving saving) {
        this.saving = saving;
    }
}
