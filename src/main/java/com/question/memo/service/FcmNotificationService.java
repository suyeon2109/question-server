package com.question.memo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.question.memo.domain.Member;
import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.ErrorResponse;
import com.question.memo.dto.fcm.PushAlarm;
import com.question.memo.dto.fcm.PushMessage;
import com.question.memo.repository.member.MemberRepository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmNotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final MemberRepository memberRepository;

	public void sendNotificationByToken(PushAlarm pushAlarm) {
		List<Member> memberList = memberRepository.findByPushAlarm(pushAlarm);

		List<ResponseEntity<?>> responses = memberList.stream()
			.filter(m -> m.getFirebaseToken() != null)
			.map(this::sendMessage)
			.collect(Collectors.toList());

		responses.forEach(response -> {
			if (!response.getStatusCode().is2xxSuccessful()) {
				log.error("push alarm send fail : {}", response.getBody());
			}
		});
	}

	private ResponseEntity<?> sendMessage(Member member) {
		try {
			Notification notification = createNotification();
			Message message = createMessage(member, notification);

			firebaseMessaging.send(message);

			return createSuccessResponse();
		} catch (FirebaseMessagingException e) {
			return createErrorResponse(e.getMessage(), member.getUuid());
		}
	}

	@NotNull
	private static ResponseEntity<ErrorResponse> createErrorResponse(String errorMessage, String uuid) {
		ErrorResponse response = ErrorResponse.builder()
			.code(468)
			.message(errorMessage)
			.description(" uuid: " + uuid)
			.build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@NotNull
	private static ResponseEntity<CommonResponse> createSuccessResponse() {
		CommonResponse response = CommonResponse.builder()
			.code(HttpStatus.OK.value())
			.message("푸시알람을 전송했습니다.")
			.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private static Message createMessage(Member member, Notification notification) {
		Message message = Message.builder()
			.setToken(member.getFirebaseToken())
			.setNotification(notification)
			.build();
		return message;
	}

	private static Notification createNotification() {
		Notification notification = Notification.builder()
			.setTitle(PushMessage.MESSAGE.getTitle() + " ✍\uD83C\uDFFB")
			.setBody(PushMessage.MESSAGE.getBody())
			.build();
		return notification;
	}
}
