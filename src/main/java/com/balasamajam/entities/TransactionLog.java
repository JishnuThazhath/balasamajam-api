package com.balasamajam.entities;

import com.balasamajam.models.TransactionType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class TransactionLog
{
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID transactionId;

    private Timestamp transactionDate;

    @ManyToOne
    @JoinColumn(name = "member_uuid")
    private Member memberId;

    private double amount;

    private double newTotal;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment paymentId;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expenseId;

    private TransactionType transactionType;

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Member getMemberId() {
        return memberId;
    }

    public void setMemberId(Member memberId) {
        this.memberId = memberId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getNewTotal() {
        return newTotal;
    }

    public void setNewTotal(double newTotal) {
        this.newTotal = newTotal;
    }

    public Payment getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payment paymentId) {
        this.paymentId = paymentId;
    }

    public Expense getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Expense expenseId) {
        this.expenseId = expenseId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @PrePersist
    private void onCreate() {
        String combo = getMemberId() + getTransactionDate().toString() + UUID.randomUUID();
        byte [] comboBytes = combo.getBytes();
        setTransactionId(UUID.nameUUIDFromBytes(comboBytes));
    }
}
