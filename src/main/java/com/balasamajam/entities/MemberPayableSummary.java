package com.balasamajam.entities;

import com.balasamajam.constants.PaymentType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class MemberPayableSummary
{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private Timestamp paymentAddedOn;

    @ManyToOne
    @JoinColumn(name = "member_uuid")
    private Member member;

    private PaymentType paymentType;

    private Float amount;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Timestamp getPaymentAddedOn()
    {
        return paymentAddedOn;
    }

    public void setPaymentAddedOn(Timestamp paymentAddedOn)
    {
        this.paymentAddedOn = paymentAddedOn;
    }

    public Member getMember()
    {
        return member;
    }

    public void setMember(Member member)
    {
        this.member = member;
    }

    public PaymentType getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType)
    {
        this.paymentType = paymentType;
    }

    public Float getAmount()
    {
        return amount;
    }

    public void setAmount(Float amount)
    {
        this.amount = amount;
    }
}
