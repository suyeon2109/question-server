package com.question.memo.dto.badge;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BadgeCreateDto {
	@NotNull
	private String badge;
	private String badgeDescription;
	private Long requiredMissions;
}
