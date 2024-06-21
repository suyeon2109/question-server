package com.question.memo.dto.mission;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionCreateDto {
	@NotNull
	private String mission;
	private String missionDescription;
	private Long missionOrder;
}
