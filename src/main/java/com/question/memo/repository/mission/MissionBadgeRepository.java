package com.question.memo.repository.mission;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.MissionBadge;

public interface MissionBadgeRepository extends JpaRepository<MissionBadge, Long> {
}
