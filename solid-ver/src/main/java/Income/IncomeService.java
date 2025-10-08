package Income;

/*
  @author Orynchuk
  @project moneycare
  @class IncomeService
  @version 1.0.0
  @since 15.09.2025 - 14.25
*/

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    private final IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public List<Income> getAll() {
        return repository.findAll();
    }

    public Optional<Income> getById(Long id) {
        return repository.findById(id);
    }

    public Income create(Income income) {
        return repository.save(income);
    }

    public Optional<Income> update(Long id, Income newIncome) {
        return repository.findById(id).map(old -> {
            old.setSource(newIncome.getSource());
            old.setCurrency(newIncome.getCurrency());
            old.setAmount(newIncome.getAmount());
            return repository.save(old);
        });
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
