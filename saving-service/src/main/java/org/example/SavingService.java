package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SavingService {

    private static final Logger log = LoggerFactory.getLogger(SavingService.class);
    @Autowired
    private SavingRepository savingRepository;

    public Map<String, BigDecimal> getAllSavingsBalance() {
        List<Saving> savings = savingRepository.findAll();
        return savings.stream()
                .collect(Collectors.toMap(Saving::getName, Saving::getCurrentBalance));
    }

    public void saveSaving(Saving saving) {
        savingRepository.save(saving);
    }

}

