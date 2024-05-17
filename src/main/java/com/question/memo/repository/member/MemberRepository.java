package com.question.memo.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.question.memo.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByMemberId(String memberId);

	Optional<Member> findByUuid(String uuid);

	Optional<Member> findByNickname(String nickname);
}
