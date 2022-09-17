package com.balasamajam.repositories;

import com.balasamajam.entities.OtherExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherExpenseRepository extends JpaRepository<OtherExpense, Long> {}
