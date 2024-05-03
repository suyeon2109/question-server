package com.question.memo.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {
	@NotNull
	private String memberId;
	@NotNull
	private String nickname;
	@NotNull
	private String uuid;
}
