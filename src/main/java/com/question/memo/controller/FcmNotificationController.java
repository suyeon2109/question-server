package com.question.memo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.fcm.FcmNotificationRequestDto;
import com.question.memo.service.FcmNotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FcmNotificationController {
	private final FcmNotificationService fcmNotificationService;

	@PostMapping("/notifications")
	public CommonResponse<String> sendNotification(@RequestBody FcmNotificationRequestDto dto) {
		fcmNotificationService.sendNotificationByToken(dto.getPushAlarm());

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("푸시알람을 전송했습니다.")
			.build();
		return response;
	}
}
