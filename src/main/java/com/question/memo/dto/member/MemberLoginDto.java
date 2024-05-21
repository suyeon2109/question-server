package com.question.memo.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginDto {
	private String memberId;
	@NotNull
	private String uuid;
	@NotNull
	private String firebaseToken;
}
