package com.balasamajam.repositories;

import com.balasamajam.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
    List<Payment> findByPaymentDateBetween(Date start, Date after);
}
