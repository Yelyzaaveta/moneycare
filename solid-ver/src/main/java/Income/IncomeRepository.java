package Income;

/*
  @author Orynchuk
  @project moneycare
  @class IncomeRepository
  @version 1.0.0
  @since 15.09.2025 - 14.25
*/

import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class IncomeRepository {
    private final Map<Long, Income> store = new HashMap<>();
    private long counter = 1;

    public IncomeRepository() {
        // початкові дані
        save(new Income(1L, "Salary", "USD", "1000"));
        save(new Income(2L, "Freelance", "EUR", "500"));
        save(new Income(3L, "Gift", "UAH", "2000"));
    }

    public List<Income> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Income> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Income save(Income income) {
        if (income.getId() == null) {
            income.setId(counter++);
        }
        store.put(income.getId(), income);
        return income;
    }

    public void delete(Long id) {
        store.remove(id);
    }
}
