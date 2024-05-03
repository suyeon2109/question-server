package com.question.memo.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberCreateDto {
	@NotNull
	private String memberId;
	@NotNull
	private String nickname;
	@NotNull
	private String uuid;
}
