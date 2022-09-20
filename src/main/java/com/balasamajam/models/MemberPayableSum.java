package com.balasamajam.models;

import com.balasamajam.constants.PaymentType;

public class MemberPayableSum
{
    private Double sum;
    private PaymentType paymentType;

    public MemberPayableSum(Double sum, PaymentType paymentType) {
        this.sum = sum;
        this.paymentType = paymentType;
    }

    public Double getSum()
    {
        return sum;
    }

    public void setSum(Double sum)
    {
        this.sum = sum;
    }

    public PaymentType getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType)
    {
        this.paymentType = paymentType;
    }
}
