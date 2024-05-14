package com.question.memo.dto.answer;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerResponseDto {
	private Long answerSeq;
	private String answer;
	private LocalDateTime answerDate;
	private String memberId;
	private String nickname;
	private Long questionSeq;
	private String question;
}
