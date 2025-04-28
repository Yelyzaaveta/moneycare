package org.example.mapper;

import org.example.dto.IncomeDTO;
import org.example.model.IncomeModel;

public class IncomeMapper {

    public static IncomeDTO toDTO(IncomeModel income) {
        return new IncomeDTO(
                income.getId(),
                income.getSource(),
                income.getCurrency(),
                income.getAmount(),
                income.getCreateDate(),
                income.getUpdateDate()
        );
    }

    public static IncomeModel toModel(IncomeDTO incomeDTO) {
        IncomeModel income = new IncomeModel();
        income.setId(incomeDTO.getId());
        income.setSource(incomeDTO.getSource());
        income.setCurrency(incomeDTO.getCurrency());
        income.setAmount(incomeDTO.getAmount());
        income.setCreateDate(incomeDTO.getCreateDate()); // Мапимо createDate
        income.setUpdateDate(incomeDTO.getUpdateDate()); // Мапимо updateDate
        return income;
    }
}
