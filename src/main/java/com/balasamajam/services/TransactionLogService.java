package com.balasamajam.services;

import com.balasamajam.entities.Expense;
import com.balasamajam.entities.Member;
import com.balasamajam.entities.Payment;
import com.balasamajam.entities.TransactionLog;
import com.balasamajam.models.TransactionType;
import com.balasamajam.repositories.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class TransactionLogService
{
    @Autowired
    private TransactionLogRepository transactionLogRepository;

    public void addNewTransactionLog(Member member, double amount, Payment paymentId, Expense expenseId, TransactionType transactionType, double newTotal)
    {
        TransactionLog transactionLog = new TransactionLog();

        transactionLog.setTransactionDate((new Timestamp(System.currentTimeMillis())));
        transactionLog.setMemberId(member);
        transactionLog.setAmount(amount);
        transactionLog.setPaymentId(paymentId);
        transactionLog.setExpenseId(expenseId);
        transactionLog.setTransactionType(transactionType);
        transactionLog.setNewTotal(newTotal);

        transactionLogRepository.save(transactionLog);
    }

    public void addNewTransactionLog(TransactionLog transactionLog)
    {
        transactionLogRepository.save(transactionLog);
    }
}
