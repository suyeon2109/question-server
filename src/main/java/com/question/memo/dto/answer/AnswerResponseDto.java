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
	private Long questionSeq;
	private String question;
	private String nickname;
	private String badge;
	private String stickerYn;
}
