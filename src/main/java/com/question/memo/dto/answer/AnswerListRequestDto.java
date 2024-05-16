package com.question.memo.dto.answer;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerListRequestDto {
	private String memberId;
	@NotNull
	private String uuid;
}
