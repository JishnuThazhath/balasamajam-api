package com.balasamajam.controllers;

import com.balasamajam.entities.Admin;
import com.balasamajam.models.*;
import com.balasamajam.services.AdminService;
import com.balasamajam.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BalasamajamAdminController
{

    @Autowired
    AdminService adminService;

    @Autowired
    LoginService loginService;

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck()
    {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/admin")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin)
    {
        adminService.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBaseModel<LoginResponseModel>> login(@RequestBody LoginRequestModel loginRequestModel)
    {
        return ResponseEntity.status(HttpStatus.OK).body(loginService.login(loginRequestModel));
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> validateLogin(@PathVariable String token) {

        try
        {
            boolean isLoggedIn = loginService.validateLogin(token);

            if(isLoggedIn)
            {
                return ResponseEntity.ok(true);
            }

        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @GetMapping("/getAllAdmins")
    public ResponseEntity<ResponseBaseModel<List<AdminBaseModel>>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }
}
