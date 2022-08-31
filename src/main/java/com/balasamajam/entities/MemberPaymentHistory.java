package com.balasamajam.entities;

import com.balasamajam.constants.PaymentType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class MemberPaymentHistory
{
    @Id
    @Column(name = "slno", nullable = false)
    private Long slno;

    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "paid_by")
    private Member paidBy;

    @ManyToOne
    @JoinColumn(name = "collected_by")
    private User collectedBy;

    private PaymentType paymentType;

    private Float amount;

    public User getCollectedBy() {
        return collectedBy;
    }

    public Long getSlno() {
        return slno;
    }

    public void setSlno(Long slno) {
        this.slno = slno;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Member getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Member paidBy) {
        this.paidBy = paidBy;
    }

    public void setCollectedBy(User collectedBy) {
        this.collectedBy = collectedBy;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

}
