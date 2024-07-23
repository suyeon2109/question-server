package com.question.memo.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PushMessage {
	MESSAGE("사각사각", "잊지마세요. 오늘 하루 돌아볼 시간입니다!");

	private String title;
	private String body;
}
