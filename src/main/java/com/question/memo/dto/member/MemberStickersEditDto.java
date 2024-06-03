package com.question.memo.dto.member;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberStickersEditDto {
	private String memberId;
	@NotNull
	private String firebaseToken;
	@NotNull
	private String stickerYn;
}
