package com.balasamajam.repositories;

import com.balasamajam.constants.ExpenseType;
import com.balasamajam.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>
{
    List<Expense> findByDateBetween(Date start, Date end);

    List<Expense> findByExpenseType(ExpenseType expenseType);
}
