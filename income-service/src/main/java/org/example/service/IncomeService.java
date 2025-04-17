package org.example.service;
import org.example.model.IncomeModel;
import org.example.mapper.IncomeMapper;
import org.example.repository.IncomeRepository;
import org.example.dto.IncomeDTO;
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
        Optional<IncomeModel> income = incomeRepository.findById(id);
        return income.map(IncomeMapper::toDTO).orElse(null);
    }

    public IncomeDTO saveIncome(IncomeDTO incomeDTO) {
        IncomeModel income = IncomeMapper.toModel(incomeDTO);
        IncomeModel savedIncome = incomeRepository.save(income);
        return IncomeMapper.toDTO(savedIncome);
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }

    public IncomeDTO updateIncome(Long id, IncomeDTO updatedIncomeDTO) {
        Optional<IncomeModel> existingIncome = incomeRepository.findById(id);
        if (existingIncome.isPresent()) {
            IncomeModel income = existingIncome.get();
            income.setSource(updatedIncomeDTO.getSource());
            income.setCurrency(updatedIncomeDTO.getCurrency());
            income.setAmount(updatedIncomeDTO.getAmount());
            IncomeModel updatedIncome = incomeRepository.save(income);
            return IncomeMapper.toDTO(updatedIncome); // використовуємо IncomeMapper для маппінгу
        }
        return null;
    }
}
