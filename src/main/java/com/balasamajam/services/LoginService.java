package com.balasamajam.services;

import com.balasamajam.entities.Admin;
import com.balasamajam.entities.Login;
import com.balasamajam.models.UserCredentials;
import com.balasamajam.repositories.AdminRepository;
import com.balasamajam.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private AdminRepository adminRepository;

    public String login(UserCredentials userCredentials)
    {
        Admin admin = adminRepository.findByUsername(userCredentials.getUsername());

        if(admin != null && admin.getPassword().equals(userCredentials.getPassword()))
        {
            String token = UUID.nameUUIDFromBytes(admin.getUsername().getBytes()).toString();

            Date utcTime = Date.from(Instant.now());
            Login login = new Login(utcTime, token, true, admin);

            loginRepository.save(login);

            return token;
        }
        else
        {
            return null;
        }
    }

    public boolean validateLogin(String token) {
        Login login = loginRepository.findByToken(token);
        return login != null;
    }
}
