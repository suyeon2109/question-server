package com.question.memo.dto.member;

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
	private String uuid;
}
