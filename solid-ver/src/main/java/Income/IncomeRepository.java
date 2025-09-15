package Income;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class IncomeRepository {
    private final Map<Long, Income> store = new HashMap<>();
    private long counter = 1;

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
