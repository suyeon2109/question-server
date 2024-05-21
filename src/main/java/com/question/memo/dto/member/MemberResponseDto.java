package com.question.memo.dto.member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.question.memo.domain.PushAlarm;

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
	private String firebaseToken;
	private Long lastQuestionId;
	private LocalDate lastQuestionDate;
	private String badge;
	private LocalDateTime badgeDate;
	private LocalDateTime createdAt;
	private String stickerYn;
	private PushAlarm pushAlarm;
}
