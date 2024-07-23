package com.question.memo.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PushAlarm {
	NONE(0, "없음"),
	MORNING(1, "오전반"),
	EVENING(2, "저녁반"),
	DAWN(3, "새벽반");

	private int code;
	private String description;
}
