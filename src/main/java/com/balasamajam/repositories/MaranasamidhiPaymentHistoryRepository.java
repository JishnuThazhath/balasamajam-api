package com.balasamajam.repositories;

import com.balasamajam.entities.MemberPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaranasamidhiPaymentHistoryRepository extends JpaRepository<MemberPaymentHistory, Long>
{
}
