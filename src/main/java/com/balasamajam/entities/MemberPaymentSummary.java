package com.balasamajam.entities;

import javax.persistence.*;

@Entity
public class MemberPaymentSummary {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_uuid")
    private Member member;

    private Float dueAmount;

    private Float paidAmount;

    private Float totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
