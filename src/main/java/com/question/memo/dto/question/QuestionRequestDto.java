package com.question.memo.dto.question;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionRequestDto {
	private String memberId;
	@NotNull
	private String firebaseToken;
}
