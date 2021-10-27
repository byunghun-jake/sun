package com.sun.tingle.member.db.repository;

import com.sun.tingle.member.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    public Optional<MemberEntity> findByMemberId(String MemberId);
    public Optional<MemberEntity> findByEmail(String email);
}
 