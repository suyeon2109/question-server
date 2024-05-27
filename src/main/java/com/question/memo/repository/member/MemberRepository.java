package com.question.memo.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.question.memo.domain.Member;
import com.question.memo.dto.fcm.PushAlarm;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByMemberId(String memberId);

	Optional<Member> findByUuid(String uuid);

	Optional<Member> findByNickname(String nickname);

	List<Member> findByPushAlarm(PushAlarm pushAlarm);

	@Query(value = "SELECT word FROM nickname WHERE type = 'ADJECTIVE' order by RAND() limit 1", nativeQuery = true)
	String findAdjective();

	@Query(value = "SELECT word FROM nickname WHERE type = 'NOUN' order by RAND() limit 1", nativeQuery = true)
	String findNoun();
}
