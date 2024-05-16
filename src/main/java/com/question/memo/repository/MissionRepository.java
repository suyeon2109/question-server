package com.question.memo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {
}
