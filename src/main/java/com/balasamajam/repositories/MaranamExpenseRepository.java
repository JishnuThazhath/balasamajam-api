package com.balasamajam.repositories;

import com.balasamajam.entities.Maranam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MaranamExpenseRepository extends JpaRepository<Maranam, UUID> {}
