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
        System.out.println("Getting all incomes...");
        List<IncomeDTO> incomes = incomeRepository.findAll().stream()
                .map(IncomeMapper::toDTO)
                .collect(Collectors.toList());
        System.out.println("Incomes fetched: " + incomes.size());
        return incomes;
    }

    public IncomeDTO getIncomeById(Long id) {
        System.out.println("Fetching income with id: " + id);
        Optional<IncomeModel> income = incomeRepository.findById(id);
        if (income.isPresent()) {
            System.out.println("Income found: " + income.get().getSource());
        } else {
            System.out.println("Income not found with id: " + id);
        }
        return income.map(IncomeMapper::toDTO).orElse(null);
    }

    public IncomeDTO saveIncome(IncomeDTO incomeDTO) {
        System.out.println("Saving new income: " + incomeDTO.getSource());
        IncomeModel income = IncomeMapper.toModel(incomeDTO);

        // Встановлення часу вручну
        income.setTimestamps();

        System.out.println("Before save - createDate: " + income.getCreateDate());
        IncomeModel savedIncome = incomeRepository.save(income);
        System.out.println("Income saved with id: " + savedIncome.getId());
        System.out.println("After save - createDate: " + savedIncome.getCreateDate());
        System.out.println("After save - updateDate: " + savedIncome.getUpdateDate());

        return IncomeMapper.toDTO(savedIncome);
    }

    public void deleteIncome(Long id) {
        System.out.println("Deleting income with id: " + id);
        incomeRepository.deleteById(id);
        System.out.println("Income deleted with id: " + id);
    }

    public IncomeDTO updateIncome(Long id, IncomeDTO updatedIncomeDTO) {
        System.out.println("Updating income with id: " + id);
        Optional<IncomeModel> existingIncome = incomeRepository.findById(id);
        if (existingIncome.isPresent()) {
            IncomeModel income = existingIncome.get();
            income.setSource(updatedIncomeDTO.getSource());
            income.setCurrency(updatedIncomeDTO.getCurrency());
            income.setAmount(updatedIncomeDTO.getAmount());

            // Встановлення часу перед оновленням
            income.setTimestamps();

            IncomeModel updatedIncome = incomeRepository.save(income);
            System.out.println("Income updated with id: " + updatedIncome.getId());
            return IncomeMapper.toDTO(updatedIncome);
        } else {
            System.out.println("Income not found for update with id: " + id);
        }
        return null;
    }
}
