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
	private String description;
	private String completedYn;
	private LocalDateTime completedAt;

	@QueryProjection
	public MissionResponseDto(Long missionSeq, String mission, String description, String completedYn,
		LocalDateTime completedAt) {
		this.missionSeq = missionSeq;
		this.mission = mission;
		this.description = description;
		this.completedYn = completedYn;
		this.completedAt = completedAt;
	}
}
