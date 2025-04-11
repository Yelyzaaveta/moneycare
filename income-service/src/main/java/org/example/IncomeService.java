package org.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public List<IncomeDTO> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .map(IncomeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public IncomeDTO getIncomeById(Long id) {
        Optional<Income> income = incomeRepository.findById(id);
        return income.map(IncomeMapper::toDTO).orElse(null);
    }

    public IncomeDTO saveIncome(IncomeDTO incomeDTO) {
        Income income = IncomeMapper.toModel(incomeDTO);
        Income savedIncome = incomeRepository.save(income);
        return IncomeMapper.toDTO(savedIncome);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public IncomeDTO updateIncome(Long id, IncomeDTO updatedIncomeDTO) {
        Optional<Income> existingIncome = incomeRepository.findById(id);
        if (existingIncome.isPresent()) {
            Income income = existingIncome.get();
            income.setSource(updatedIncomeDTO.getSource());
            income.setCurrency(updatedIncomeDTO.getCurrency());
            income.setAmount(updatedIncomeDTO.getAmount());
            Income updatedIncome = incomeRepository.save(income);
            return IncomeMapper.toDTO(updatedIncome); // використовуємо IncomeMapper для маппінгу
        }
        return null;
    }
}
