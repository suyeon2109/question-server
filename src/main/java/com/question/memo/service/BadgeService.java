package com.question.memo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Badge;
import com.question.memo.dto.mission.MissionCreateDto;
import com.question.memo.repository.BadgeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BadgeService {
	private final BadgeRepository badgeRepository;
	public Long saveBadge(MissionCreateDto dto) {
		Badge badge = badgeRepository.save(Badge.builder()
			.badge(dto.getBadge())
			.description(dto.getBadgeDescription())
			.badgeOrder(dto.getBadgeOrder())
			.build());
		return badge.getBadgeSeq();
	}
}
