package com.balasamajam;

import com.balasamajam.entities.User;
import com.balasamajam.models.LoginResponse;
import com.balasamajam.models.Output;
import com.balasamajam.models.UserCredentials;
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

    @PostMapping("/admin")
    public ResponseEntity<String> addAdmin(@RequestBody User user)
    {
        adminService.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<Output> login(@RequestBody UserCredentials userCredentials) {
        String token = loginService.login(userCredentials);

        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
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
            else
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
