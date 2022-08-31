package com.balasamajam.repositories;

import com.balasamajam.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<User, UUID> {
    public User findByUsername(String username);
}
