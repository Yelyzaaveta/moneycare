package org.example.architecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.model.IncomeModel;
import org.example.repository.IncomeRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/*
  @author Orynchuk
  @project micro
  @class RepositoryTestes
  @version 1.0.0
  @since 23.04.2025 - 23.45
*/

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryTests {

    @Autowired
    IncomeRepository underTest;

    @BeforeAll
    void beforeAll() {}

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
    void whenIncomeSaved_ThenIdIsGenerated() {
        IncomeModel income = new IncomeModel(null, "Investment", "USD", new BigDecimal("1000"));
        IncomeModel saved = underTest.save(income);
        assertNotNull(saved.getId());
    }

    @Test
    void whenGetRecords_ToTest_ThenExpect_3_Records() {
        List<IncomeModel> incomes = underTest.findAll();
        assertEquals(3, incomes.size());
    }

    @Test
    void whenFindingBySourceSalary_ThenExpect_2_Records() {
        List<IncomeModel> salaries = underTest.findBySource("Salary");
        assertEquals(2, salaries.size());
    }

    @Test
    void whenFindingByNonExistingSource_ThenExpectEmptyList() {
        List<IncomeModel> bonuses = underTest.findBySource("Bonus");
        assertTrue(bonuses.isEmpty());
    }

    @Test
    void whenDeletingById_ThenRecordIsRemoved() {
        IncomeModel income = new IncomeModel(null, "Bonus", "UAH", new BigDecimal("1000"));
        IncomeModel saved = underTest.save(income);
        underTest.deleteById(saved.getId());

        Optional<IncomeModel> deleted = underTest.findById(saved.getId());
        assertTrue(deleted.isEmpty());
    }

    @Test
    void whenUpdatingAmount_ThenNewAmountIsSaved() {
        IncomeModel income = underTest.findBySource("Freelance").get(0);
        income.setAmount(new BigDecimal("999.99"));
        underTest.save(income);

        IncomeModel updated = underTest.findById(income.getId()).orElseThrow();
        assertEquals(new BigDecimal("999.99"), updated.getAmount());
    }

    @Test
    void whenFindingById_ThenCorrectIncomeIsReturned() {
        IncomeModel income = underTest.findAll().get(0);
        Optional<IncomeModel> result = underTest.findById(income.getId());
        assertTrue(result.isPresent());
        assertEquals(income.getSource(), result.get().getSource());
    }

    @Test
    void whenRepositoryIsEmpty_ThenFindAllReturnsEmptyList() {
        underTest.deleteAll();
        List<IncomeModel> incomes = underTest.findAll();
        assertTrue(incomes.isEmpty());
    }

    @Test
    void whenSavingMultipleIncomes_ThenAllArePersisted() {
        underTest.saveAll(List.of(
                new IncomeModel(null, "Grant", "USD", new BigDecimal("1000")),
                new IncomeModel(null, "Scholarship", "UAH", new BigDecimal("1200"))
        ));
        assertEquals(5, underTest.findAll().size());
    }

    @Test
    void whenSavingIncomeWithNullAmount_ThenNoCrashOccurs() {
        IncomeModel income = new IncomeModel(null, "Gift", "USD", null);
        IncomeModel saved = underTest.save(income);
        assertNull(saved.getAmount());
    }

}
