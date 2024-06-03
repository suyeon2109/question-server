package com.question.memo.dto.member;

import java.time.LocalDateTime;

import com.question.memo.dto.fcm.PushAlarm;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberEditDto {

	private String memberId;
	private String nickname;
	private String email;
	private String ageRange;
	private String guestYn;
	private String firebaseToken;
	private LocalDateTime createdAt;
	private String stickerYn;
	private PushAlarm pushAlarm;
}
