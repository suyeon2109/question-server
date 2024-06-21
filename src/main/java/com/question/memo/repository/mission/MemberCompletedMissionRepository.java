package com.question.memo.repository.mission;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.question.memo.domain.Member;
import com.question.memo.domain.MemberCompletedMission;

public interface MemberCompletedMissionRepository extends JpaRepository<MemberCompletedMission, Long> {
	List<MemberCompletedMission> findByMember(Member member);
}
