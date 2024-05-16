package com.question.memo.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {
	private Long memberSeq;
	private String memberId;
	private String nickname;
	private String email;
	private String ageRange;
	private String guestYn;
	private String uuid;
	private Long last_question_id;
	private LocalDate lastQuestionDate;
	private Long badge_id;
	private LocalDateTime badgeDate;
}
