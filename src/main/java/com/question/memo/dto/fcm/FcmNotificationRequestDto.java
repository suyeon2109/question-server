package com.question.memo.dto.fcm;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmNotificationRequestDto {
	private String memberId;
	@NotNull
	private String uuid;
	private String title;
	private String body;
	private String image;
	private Map<String, String> data;
}
