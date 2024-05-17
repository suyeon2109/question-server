package com.question.memo.dto.mission;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionRequestDto {
	private String memberId;
	@NotNull
	private String uuid;
}
