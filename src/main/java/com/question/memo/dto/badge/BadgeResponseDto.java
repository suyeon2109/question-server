package com.question.memo.dto.badge;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BadgeResponseDto {
	private Long badgeSeq;
	private String badge;
	private String description;
	private Long requiredMissions;
	private String receivedYn;
	private LocalDateTime receivedAt;

	@QueryProjection
	public BadgeResponseDto(Long badgeSeq, String badge, String description, Long requiredMissions, String receivedYn,
		LocalDateTime receivedAt) {
		this.badgeSeq = badgeSeq;
		this.badge = badge;
		this.description = description;
		this.requiredMissions = requiredMissions;
		this.receivedYn = receivedYn;
		this.receivedAt = receivedAt;
	}
}
