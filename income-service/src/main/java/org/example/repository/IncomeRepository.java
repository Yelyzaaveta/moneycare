package org.example.repository;

import org.example.model.IncomeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeModel, Long> {
    List<IncomeModel> findBySource(String source);
}
