package com.question.memo.dto.badge;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BadgeRequestDto {
	private String memberId;
	@NotNull
	private String firebaseToken;
}
