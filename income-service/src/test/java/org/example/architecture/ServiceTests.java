package org.example.architecture;

import org.example.dto.IncomeDTO;
import org.example.model.IncomeModel;
import org.example.repository.IncomeRepository;
import org.example.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
  @author Orynchuk
  @project micro
  @class ServiceTests
  @version 1.0.0
  @since 28.04.2025 - 16.57
*/

@SpringBootTest
class ServiceTests {

    @Autowired
    private IncomeRepository repository;

    @Autowired
    private IncomeService underTest;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void whenSaveNewIncome_thenItIsSavedCorrectly() {
        IncomeDTO request = new IncomeDTO(null, "Freelance", "USD", new BigDecimal("3000"), null, null);
        LocalDateTime now = LocalDateTime.now();

        // when
        IncomeDTO savedIncome = underTest.saveIncome(request);

        // then
        assertNotNull(savedIncome);
        assertNotNull(savedIncome.getId());
        assertEquals("Freelance", savedIncome.getSource());
        assertEquals("USD", savedIncome.getCurrency());
        assertEquals(new BigDecimal("3000"), savedIncome.getAmount());
        assertNotNull(savedIncome.getCreateDate());
        assertNotNull(savedIncome.getUpdateDate());
        assertSame(LocalDateTime.class, savedIncome.getCreateDate().getClass());
        assertSame(LocalDateTime.class, savedIncome.getUpdateDate().getClass());
        assertTrue(savedIncome.getCreateDate().isAfter(now));
        assertTrue(savedIncome.getUpdateDate().isAfter(now));

    }

    @Test
    void whenSaveIncomeWithEmptyFields_thenItIsHandledCorrectly() {
        IncomeDTO request = new IncomeDTO(null, "", "", new BigDecimal("0"), null, null);

        // when
        IncomeDTO savedIncome = underTest.saveIncome(request);

        // then
        assertNotNull(savedIncome);
        assertEquals("", savedIncome.getSource());
        assertEquals("", savedIncome.getCurrency());
        assertEquals(new BigDecimal("0"), savedIncome.getAmount());
        assertNotNull(savedIncome.getCreateDate());
        assertNotNull(savedIncome.getUpdateDate());
    }

    @Test
    void whenGetIncomeById_thenReturnCorrectIncome() {
        IncomeModel model = new IncomeModel(null, "Salary", "EUR", new BigDecimal("2500"));
        IncomeModel savedModel = repository.save(model);
        IncomeDTO foundIncome = underTest.getIncomeById(savedModel.getId());
        assertNotNull(foundIncome);
        assertEquals("Salary", foundIncome.getSource());
    }

    @Test
    void whenUpdateIncome_thenFieldsAreUpdated() {
        IncomeModel model = new IncomeModel(null, "Gift", "USD", new BigDecimal("100"));
        IncomeModel savedModel = repository.save(model);
        IncomeDTO updateRequest = new IncomeDTO(null, "Updated Gift", "UAH", new BigDecimal("150"), null, null);
        IncomeDTO updatedIncome = underTest.updateIncome(savedModel.getId(), updateRequest);
        assertEquals("Updated Gift", updatedIncome.getSource());
        assertEquals("UAH", updatedIncome.getCurrency());
    }

    @Test
    void whenDeleteIncome_thenItIsRemoved() {
        IncomeModel model = new IncomeModel(null, "Prize", "USD", new BigDecimal("500"));
        IncomeModel savedModel = repository.save(model);
        underTest.deleteIncome(savedModel.getId());
        assertTrue(repository.findById(savedModel.getId()).isEmpty());
    }

    @Test
    void whenGetAllIncomes_thenReturnList() {
        repository.save(new IncomeModel(null, "Bonus", "USD", new BigDecimal("1000")));
        repository.save(new IncomeModel(null, "Scholarship", "EUR", new BigDecimal("200")));
        List<IncomeDTO> incomes = underTest.getAllIncomes();
        assertEquals(2, incomes.size());
    }

    @Test
    void whenSavingIncomeWithZeroAmount_thenSaveSuccessfully() {
        IncomeDTO request = new IncomeDTO(null, "Zero Income", "USD", BigDecimal.ZERO, null, null);
        IncomeDTO saved = underTest.saveIncome(request);
        assertEquals(BigDecimal.ZERO, saved.getAmount());
    }

    @Test
    void whenSavingIncomeWithNegativeAmount_thenSaveSuccessfully() {
        IncomeDTO request = new IncomeDTO(null, "Negative Income", "USD", new BigDecimal("-100"), null, null);
        IncomeDTO saved = underTest.saveIncome(request);
        assertEquals(new BigDecimal("-100"), saved.getAmount());
    }

    @Test
    void whenUpdatingOnlyAmount_thenAmountUpdated() {
        IncomeModel model = repository.save(new IncomeModel(null, "Source", "USD", new BigDecimal("100")));
        IncomeDTO updateRequest = new IncomeDTO(null, null, null, new BigDecimal("200"), null, null);
        IncomeDTO updated = underTest.updateIncome(model.getId(), updateRequest);
        assertEquals(new BigDecimal("200"), updated.getAmount());
    }

    @Test
    void whenDeletingNonExistingIncome_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> underTest.deleteIncome(999L));
    }

    @Test
    void whenSavingNullSource_thenSaveSuccessfully() {
        IncomeDTO request = new IncomeDTO(null, null, "USD", new BigDecimal("100"), null, null);
        IncomeDTO saved = underTest.saveIncome(request);
        assertNull(saved.getSource());
    }

    @Test
    void whenSavingNullCurrency_thenSaveSuccessfully() {
        IncomeDTO request = new IncomeDTO(null, "Freelance", null, new BigDecimal("100"), null, null);
        IncomeDTO saved = underTest.saveIncome(request);
        assertNull(saved.getCurrency());
    }

    @Test
    void whenGetIncomeByNonExistingId_thenReturnNull() {
        // when
        IncomeDTO foundIncome = underTest.getIncomeById(999L);

        // then
        assertNull(foundIncome);
    }

    @Test
    void whenUpdateIncomeWithNonExistingId_thenReturnNull() {
        // given
        IncomeDTO updateRequest = new IncomeDTO(null, "Updated Gift", "UAH", new BigDecimal("200"), null, null);

        // when
        IncomeDTO updatedIncome = underTest.updateIncome(999L, updateRequest);

        // then
        assertNull(updatedIncome);
    }

    @Test
    void whenSaveMultipleIncomes_thenAllAreSaved() {
        // given
        IncomeDTO income1 = new IncomeDTO(null, "Salary", "USD", new BigDecimal("3000"), null, null);
        IncomeDTO income2 = new IncomeDTO(null, "Freelance", "EUR", new BigDecimal("1500"), null, null);

        // when
        underTest.saveIncome(income1);
        underTest.saveIncome(income2);

        // then
        assertEquals(2, repository.count());
    }

    @Test
    void whenGetAllIncomesFromEmptyRepository_thenReturnEmptyList() {
        // when
        var incomes = underTest.getAllIncomes();

        // then
        assertTrue(incomes.isEmpty());
    }

    @Test
    void whenSavingMassiveIncomes_thenAllAreSaved() {
        for (int i = 0; i < 100; i++) {
            IncomeDTO request = new IncomeDTO(null, "Source " + i, "USD", BigDecimal.valueOf(i), null, null);
            underTest.saveIncome(request);
        }
        List<IncomeDTO> incomes = underTest.getAllIncomes();
        assertEquals(100, incomes.size());
    }

    @Test
    void whenSavingIncomeWithBigAmount_thenSaveSuccessfully() {
        IncomeDTO request = new IncomeDTO(null, "Big Win", "USD", new BigDecimal("1000000000"), null, null);
        IncomeDTO saved = underTest.saveIncome(request);
        assertEquals(new BigDecimal("1000000000"), saved.getAmount());
    }

    @Test
    void whenDeletingAllIncomes_thenAllRemoved() {
        repository.save(new IncomeModel(null, "A", "USD", new BigDecimal("1")));
        repository.save(new IncomeModel(null, "B", "USD", new BigDecimal("2")));
        repository.deleteAll();
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void whenSavingDuplicatedIncomes_thenAllAreSavedSeparately() {
        IncomeDTO request = new IncomeDTO(null, "Duplicate", "USD", new BigDecimal("100"), null, null);
        underTest.saveIncome(request);
        underTest.saveIncome(request);
        List<IncomeDTO> incomes = underTest.getAllIncomes();
        assertEquals(2, incomes.size());
    }
}
