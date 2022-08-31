package com.balasamajam;

import com.balasamajam.entities.MemberPaymentHistory;
import com.balasamajam.services.MaranasamidhiPirivuDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/maranasamidhi")
public class BalasamajamMaranasamidhiController
{
    @Autowired
    private MaranasamidhiPirivuDetailsService maranasamidhiPirivuDetailsService;

    @GetMapping("/pirivudetails")
    public ResponseEntity<List<MemberPaymentHistory>> getPirivuDetails()
    {
        return ResponseEntity.ok(maranasamidhiPirivuDetailsService.getPirivuDetails());
    }

    @GetMapping("/addpirivudetails")
    public ResponseEntity<Boolean> addPirivuDetails(@RequestBody MemberPaymentHistory maranasamidhiPirivuDetails)
    {
        return ResponseEntity.ok(maranasamidhiPirivuDetailsService.addPirivuDetails(maranasamidhiPirivuDetails));
    }
}
