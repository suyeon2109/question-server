package com.question.memo.dto.answer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequestDto {
	@NotNull
	private Long questionSeq;
	@NotNull
	private String answer;
}
