package com.question.memo.repository.badge;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.Badge;
import com.question.memo.domain.Member;
import com.question.memo.domain.MemberReceivedBadge;

public interface MemberReceivedBadgeRepository extends JpaRepository<MemberReceivedBadge, Long> {
	Optional<MemberReceivedBadge> findByMemberAndBadge(Member member, Badge badge);
}
