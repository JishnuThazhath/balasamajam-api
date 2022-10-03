package com.balasamajam.services;

import com.balasamajam.entities.Admin;
import com.balasamajam.jwt.JwtTokenUtil;
import com.balasamajam.models.LoginRequestModel;
import com.balasamajam.models.LoginResponseModel;
import com.balasamajam.models.ResponseBaseModel;
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

    public ResponseBaseModel<LoginResponseModel> login(LoginRequestModel loginRequestModel)
    {
        ResponseBaseModel<LoginResponseModel> loginResponseModel = new ResponseBaseModel<>();
        try
        {
            Admin admin = adminRepository.findByUsername(loginRequestModel.getUsername());
            LoginResponseModel loginResponse = new LoginResponseModel();

            if(admin != null && admin.getPassword().equals(loginRequestModel.getPassword()))
            {
                String token = jwtTokenUtil.doGenerateToken(admin.getUsername());

                loginResponseModel.setMessage("Login Successful");
                loginResponseModel.setStatus("OK");

                loginResponse.setToken(token);
                loginResponse.setAddress(admin.getAddress());
                loginResponse.setFirstName(admin.getFirstName());
                loginResponse.setLastName(admin.getLastName());
                loginResponse.setPhone(admin.getPhone());

                loginResponseModel.setData(loginResponse);
            }
            else
            {
                loginResponseModel.setMessage("Username or Password is Incorrect");
                loginResponseModel.setStatus("FAILED");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            loginResponseModel.setMessage("Login Failed");
            loginResponseModel.setStatus("ERROR");

        }
        return loginResponseModel;
    }

    public boolean validateLogin(String token) {
        return jwtTokenUtil.isValidToken(token);
    }
}
