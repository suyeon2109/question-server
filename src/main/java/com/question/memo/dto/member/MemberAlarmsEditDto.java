package com.question.memo.dto.member;

import com.question.memo.dto.fcm.PushAlarm;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAlarmsEditDto {
	@NotNull
	private PushAlarm pushAlarm;
}
