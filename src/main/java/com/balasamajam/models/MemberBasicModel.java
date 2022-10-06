package com.balasamajam.models;

import java.util.UUID;

public class MemberBasicModel
{
    private String memberFullName;

    private UUID memberId;

    public String getMemberFullName() {
        return memberFullName;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }
}
