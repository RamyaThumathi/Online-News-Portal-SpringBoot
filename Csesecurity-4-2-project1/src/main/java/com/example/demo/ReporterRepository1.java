package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReporterRepository1 extends JpaRepository<Reporter, Long> {
    Optional<Reporter> findByUsername(String username);
}
