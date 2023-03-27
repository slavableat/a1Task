package com.example.demo.repository;

import com.example.demo.model.Crcy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrcyRepository extends JpaRepository<Crcy, Long> {
    Optional<Crcy> findByTitle(String title);
}
