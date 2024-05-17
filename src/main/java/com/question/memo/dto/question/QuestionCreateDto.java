package com.question.memo.dto.question;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionCreateDto {
	@NotNull
	private String question;
	private Long questionOrder;
	private String guestYn;
}
