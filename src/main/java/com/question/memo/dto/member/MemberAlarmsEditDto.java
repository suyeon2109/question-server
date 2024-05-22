package com.question.memo.dto.member;

import com.question.memo.dto.fcm.PushAlarm;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberAlarmsEditDto {
	private String memberId;
	@NotNull
	private String uuid;
	@NotNull
	private PushAlarm pushAlarm;
}
