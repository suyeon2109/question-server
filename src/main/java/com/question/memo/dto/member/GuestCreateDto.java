package com.question.memo.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuestCreateDto {
	@NotNull
	private String uuid;
}
