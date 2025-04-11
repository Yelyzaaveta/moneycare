package org.example;

public class IncomeMapper {

    public static IncomeDTO toDTO(Income income) {
        return new IncomeDTO(income.getId(), income.getSource(), income.getCurrency(), income.getAmount());
    }

    public static Income toModel(IncomeDTO incomeDTO) {
        return new Income(incomeDTO.getId(), incomeDTO.getSource(), incomeDTO.getCurrency(), incomeDTO.getAmount());
    }
}
