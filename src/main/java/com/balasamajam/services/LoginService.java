package com.balasamajam.services;

import com.balasamajam.entities.Admin;
import com.balasamajam.entities.Login;
import com.balasamajam.jwt.JwtTokenUtil;
import com.balasamajam.models.LoginResponse;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String login(UserCredentials userCredentials)
    {
        try
        {
            Admin admin = adminRepository.findByUsername(userCredentials.getUsername());

            if(admin != null && admin.getPassword().equals(userCredentials.getPassword()))
            {
                String token = jwtTokenUtil.doGenerateToken(admin.getUsername());

//                Date utcTime = Date.from(Instant.now());
//                Login login = new Login(utcTime, token, true, admin);

//                loginRepository.save(login);

                return token;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean logout(String token) {
        try
        {
            Login login = loginRepository.findByToken(token);
            if(login == null)
            {
                System.out.println("User is not logged in.");
            }
            else
            {
                loginRepository.delete(login);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateLogin(String token) {
//        Login login = loginRepository.findByToken(token);
//        return login != null;
        return jwtTokenUtil.isValidToken(token);
    }
}
