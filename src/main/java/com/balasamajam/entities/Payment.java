package com.balasamajam.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Payment
{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Timestamp paymentDate;

    private double paymentAmount;

    @ManyToOne
    @JoinColumn(name = "collected_by_uuid")
    private Admin collectedBy;

    private String comments;

    @ManyToOne
    @JoinColumn(name = "collected_from_uuid")
    private Member collectedFrom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Admin getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(Admin collectedBy) {
        this.collectedBy = collectedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Member getCollectedFrom() {
        return collectedFrom;
    }

    public void setCollectedFrom(Member collectedFrom) {
        this.collectedFrom = collectedFrom;
    }
}
