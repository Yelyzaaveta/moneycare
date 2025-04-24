package org.example.architecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.model.IncomeModel;
import org.example.repository.IncomeRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTests {

    @Autowired
    IncomeRepository underTest;

    @BeforeEach
    void setUp() {
        underTest.saveAll(List.of(
                new IncomeModel(null, "Salary", "USD", new BigDecimal("2000.00")),
                new IncomeModel(null, "Freelance", "EUR", new BigDecimal("500.00")),
                new IncomeModel(null, "Salary", "UAH", new BigDecimal("18000.00"))
        ));
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void whenSavingNewIncome_ThenGeneratedIdIsNotNull() {
        // Given
        IncomeModel income = new IncomeModel(null, "Investment", "USD", new BigDecimal("1000"));

        // When
        underTest.save(income);
        IncomeModel savedIncome = underTest.findAll().stream()
                .filter(i -> i.getSource().equals("Investment"))
                .findFirst().orElse(null);

        // Then
        assertNotNull(savedIncome);
        assertNotNull(savedIncome.getId());
        assertTrue(savedIncome.getId() > 0);
        assertEquals("Investment", savedIncome.getSource());
    }

    @Test
    void whenGetRecords_ThenReturn3Records() {
        // Given setUp()

        // When
        List<IncomeModel> incomes = underTest.findAll();

        // Then
        assertEquals(3, incomes.size());
        assertTrue(incomes.stream().anyMatch(i -> "Salary".equals(i.getSource())));
        assertTrue(incomes.stream().anyMatch(i -> "Freelance".equals(i.getSource())));
    }

    @Test
    void whenFindingBySourceSalary_ThenReturn2Records() {
        // Given
        String source = "Salary";

        // When
        List<IncomeModel> salaries = underTest.findBySource(source);

        // Then
        assertEquals(2, salaries.size());
        assertTrue(salaries.stream().allMatch(i -> "Salary".equals(i.getSource())));
    }

    @Test
    void whenFindingByNonExistingSource_ThenReturnEmptyList() {
        // Given
        String source = "Bonus";

        // When
        List<IncomeModel> bonuses = underTest.findBySource(source);

        // Then
        assertTrue(bonuses.isEmpty());
        assertEquals(0, bonuses.size());
    }

    @Test
    void whenDeletingById_ThenRecordIsRemoved() {
        // Given
        IncomeModel income = new IncomeModel(null, "Bonus", "UAH", new BigDecimal("1000"));
        IncomeModel saved = underTest.save(income);

        // When
        underTest.deleteById(saved.getId());

        // Then
        Optional<IncomeModel> deleted = underTest.findById(saved.getId());
        assertTrue(deleted.isEmpty());
        assertFalse(underTest.findAll().contains(saved));
    }

    @Test
    void whenUpdatingAmount_ThenNewAmountIsSaved() {
        // Given
        IncomeModel income = underTest.findBySource("Freelance").get(0);
        income.setAmount(new BigDecimal("999.99"));

        // When
        underTest.save(income);
        IncomeModel updated = underTest.findById(income.getId()).orElseThrow();

        // Then
        assertEquals(new BigDecimal("999.99"), updated.getAmount());
        assertNotEquals(new BigDecimal("500.00"), updated.getAmount());
    }

    @Test
    void whenFindingById_ThenCorrectIncomeIsReturned() {
        // Given
        IncomeModel income = underTest.findAll().get(0);

        // When
        Optional<IncomeModel> result = underTest.findById(income.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals(income.getSource(), result.get().getSource());
        assertEquals("USD", result.get().getCurrency());
    }

    @Test
    void whenRepositoryIsEmpty_ThenFindAllReturnsEmptyList() {
        // Given
        underTest.deleteAll();

        // When
        List<IncomeModel> incomes = underTest.findAll();

        // Then
        assertTrue(incomes.isEmpty());
        assertEquals(0, incomes.size());
    }

    @Test
    void whenSavingMultipleIncomes_ThenAllArePersisted() {
        // Given
        List<IncomeModel> newIncomes = List.of(
                new IncomeModel(null, "Grant", "USD", new BigDecimal("1000")),
                new IncomeModel(null, "Scholarship", "UAH", new BigDecimal("1200"))
        );

        // When
        underTest.saveAll(newIncomes);

        // Then
        List<IncomeModel> allIncomes = underTest.findAll();
        assertEquals(5, allIncomes.size());
        assertTrue(allIncomes.stream().anyMatch(i -> "Grant".equals(i.getSource())));
        assertTrue(allIncomes.stream().anyMatch(i -> "Scholarship".equals(i.getSource())));
    }

    @Test
    void whenSavingIncomeWithNullAmount_ThenNoCrashOccurs() {
        // Given
        IncomeModel income = new IncomeModel(null, "Gift", "USD", null);

        // When
        IncomeModel saved = underTest.save(income);

        // Then
        assertNull(saved.getAmount());
        assertEquals("Gift", saved.getSource());
    }
}
