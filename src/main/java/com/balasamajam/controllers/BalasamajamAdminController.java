package com.balasamajam.controllers;

import com.balasamajam.entities.Admin;
import com.balasamajam.models.LoginResponseModel;
import com.balasamajam.models.Output;
import com.balasamajam.models.LoginRequestModel;
import com.balasamajam.models.ResponseBaseModel;
import com.balasamajam.services.AdminService;
import com.balasamajam.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/logout/{token}")
    public ResponseEntity<Output> logout(@PathVariable String token)
    {
        System.out.println("Logout request received");
        // one another wat to do is to set a claim attribure that says if logged out or not.
        // based on that binary attribute we could decide if the user is logged out or not.
        return ResponseEntity.status(HttpStatus.OK).body(loginService.logout(token));
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
}
