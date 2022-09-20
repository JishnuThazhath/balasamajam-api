package com.balasamajam.repositories;

import com.balasamajam.constants.PaymentType;
import com.balasamajam.entities.Member;
import com.balasamajam.entities.MemberPayableSummary;
import com.balasamajam.models.MemberPayableSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberPayableSummaryRepository extends JpaRepository<MemberPayableSummary, Long>
{
    @Query("SELECT new com.balasamajam.models.MemberPayableSum(SUM(summary.amount), summary.paymentType) FROM MemberPayableSummary summary WHERE summary.member = :member GROUP BY summary.paymentType")
    List<MemberPayableSum> findMaranavariSum(Member member);
}
