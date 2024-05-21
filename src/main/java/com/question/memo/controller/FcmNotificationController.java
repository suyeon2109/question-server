package com.question.memo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.fcm.FcmNotificationRequestDto;
import com.question.memo.service.FcmNotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FcmNotificationController {
	private final FcmNotificationService fcmNotificationService;

	@PostMapping("/notifications")
	public CommonResponse<String> sendNotification(@Valid @RequestBody FcmNotificationRequestDto dto, BindingResult e) throws
		FirebaseMessagingException {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		fcmNotificationService.sendNotificationByToken(dto);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("푸쉬알람을 전송했습니다.")
			.build();
		return response;
	}
}
