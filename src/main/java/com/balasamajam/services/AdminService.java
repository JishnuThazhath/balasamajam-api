package com.balasamajam.services;

import com.balasamajam.entities.User;
import com.balasamajam.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public void save(User user) {
        adminRepository.save(user);
    }
}
