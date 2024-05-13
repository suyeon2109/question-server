package com.question.memo.dto.question;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponseDto {
	private Long questionSeq;
	private String question;
	private Long questionOrder;
}
