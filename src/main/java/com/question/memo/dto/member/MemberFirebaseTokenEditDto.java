package com.question.memo.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberFirebaseTokenEditDto {
	private String memberId;
	@NotNull
	private String currentFirebaseToken;
	@NotNull
	private String newFirebaseToken;
}
