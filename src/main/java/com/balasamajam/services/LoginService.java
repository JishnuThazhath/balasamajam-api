package com.balasamajam.services;

import com.balasamajam.entities.Admin;
import com.balasamajam.entities.Login;
import com.balasamajam.jwt.JwtTokenUtil;
import com.balasamajam.models.LoginRequestModel;
import com.balasamajam.models.LoginResponseModel;
import com.balasamajam.models.Output;
import com.balasamajam.repositories.AdminRepository;
import com.balasamajam.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public LoginResponseModel login(LoginRequestModel loginRequestModel)
    {
        LoginResponseModel loginResponseModel;
        try
        {
            Admin admin = adminRepository.findByUsername(loginRequestModel.getUsername());

            if(admin != null && admin.getPassword().equals(loginRequestModel.getPassword()))
            {
                String token = jwtTokenUtil.doGenerateToken(admin.getUsername());
                loginResponseModel = new LoginResponseModel(token, "OK", "Login Successfull");
            }
            else
            {
                loginResponseModel = new LoginResponseModel(null, "FAILED", "Username or Password is Incorrect");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            loginResponseModel = new LoginResponseModel(null, "ERROR", "Login Failed");
        }
        return loginResponseModel;
    }

    public Output logout(String token) {
        return new Output("OK", "Nothing todo here. Remove token from client cache");
    }

    public boolean validateLogin(String token) {
        return jwtTokenUtil.isValidToken(token);
    }
}
