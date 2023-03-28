package com.example.demo.repository;

import com.example.demo.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findByPostingDateBetweenAndIsAuthorized(LocalDate start, LocalDate end, Boolean isAuthorized);
    List<Posting> findByPostingDateBetween(LocalDate start, LocalDate end);
}
