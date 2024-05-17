package com.question.memo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
