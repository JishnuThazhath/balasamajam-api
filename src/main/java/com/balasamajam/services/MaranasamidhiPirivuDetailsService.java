package com.balasamajam.services;

import com.balasamajam.entities.MemberPaymentHistory;
import com.balasamajam.repositories.MaranasamidhiPaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaranasamidhiPirivuDetailsService
{
    @Autowired
    private MaranasamidhiPaymentHistoryRepository repository;

    public List<MemberPaymentHistory> getPirivuDetails()
    {
        return repository.findAll();
    }

    public boolean addPirivuDetails(MemberPaymentHistory maranasamidhiPirivuDetails)
    {
        try
        {
            repository.save(maranasamidhiPirivuDetails);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
