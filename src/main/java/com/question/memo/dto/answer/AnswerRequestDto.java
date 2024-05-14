package com.question.memo.dto.answer;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerRequestDto {
	@NotNull
	private String memberId;
	@NotNull
	private Long questionSeq;
	@NotNull
	private String answer;
}
