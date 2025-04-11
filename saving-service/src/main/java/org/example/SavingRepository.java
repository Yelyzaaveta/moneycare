package org.example;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SavingRepository extends JpaRepository<Saving, Long> {
    Optional<Saving> findByName(String name);
}
