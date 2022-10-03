package com.balasamajam.repositories;

import com.balasamajam.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID>
{
    @Query("SELECT m from Member m WHERE LOWER(m.fullName) LIKE %?1%")
    List<Member> findMembers(String searchKey);
}
