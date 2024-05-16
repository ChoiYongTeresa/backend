package com.choiteresa.fonation.domain.member.repository;

import com.choiteresa.fonation.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {

    Optional<Member> findByUserId(String user_id);


}
