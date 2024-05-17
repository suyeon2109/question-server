package com.question.memo.dto.mission;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionResponseDto {
	private Long missionSeq;
	private String mission;
	private String missionDescription;
	private Long badgeSeq;
	private String badge;
	private String badgeDescription;
	private String completedYn;
	private LocalDateTime completedAt;

	@QueryProjection
	public MissionResponseDto(Long missionSeq, String mission, String missionDescription, Long badgeSeq, String badge,
		String badgeDescription, String completedYn, LocalDateTime completedAt) {
		this.missionSeq = missionSeq;
		this.mission = mission;
		this.missionDescription = missionDescription;
		this.badgeSeq = badgeSeq;
		this.badge = badge;
		this.badgeDescription = badgeDescription;
		this.completedYn = completedYn;
		this.completedAt = completedAt;
	}
}
