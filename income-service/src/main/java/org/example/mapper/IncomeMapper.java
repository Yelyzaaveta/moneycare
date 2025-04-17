package org.example.mapper;

import org.example.dto.IncomeDTO;
import org.example.model.IncomeModel;

public class IncomeMapper {

    public static IncomeDTO toDTO(IncomeModel income) {
        return new IncomeDTO(income.getId(), income.getSource(), income.getCurrency(), income.getAmount());
    }

    public static IncomeModel toModel(IncomeDTO incomeDTO) {
        return new IncomeModel(incomeDTO.getId(), incomeDTO.getSource(), incomeDTO.getCurrency(), incomeDTO.getAmount());
    }
}
