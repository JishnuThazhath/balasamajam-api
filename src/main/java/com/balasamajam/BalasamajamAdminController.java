package com.balasamajam;

import com.balasamajam.entities.Admin;
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
    public ResponseEntity<Output> login(@RequestBody UserCredentials userCredentials)
    {
        String token = loginService.login(userCredentials);
        System.out.println("username > " + userCredentials.getUsername());
        System.out.println("password > " + userCredentials.getPassword());
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Output("Failed"));
        }

        LoginResponse loginResponse = new LoginResponse(token, "Successfull");

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @GetMapping("/logout/{token}")
    public ResponseEntity<Boolean> logout(@PathVariable String token)
    {
        System.out.println("Logout request received");
        boolean isLoggedOut = loginService.logout(token);

        return ResponseEntity.ok(isLoggedOut);
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
