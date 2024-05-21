package com.question.memo.service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.question.memo.domain.Member;
import com.question.memo.dto.fcm.FcmNotificationRequestDto;
import com.question.memo.exception.DeviceNotMatchedException;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FcmNotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final MemberRepository memberRepository;

	public void sendNotificationByToken(FcmNotificationRequestDto requestDto) throws FirebaseMessagingException {
		Member member = getMemberInfo(requestDto.getMemberId(), requestDto.getUuid());

		if (member.getFirebaseToken() != null) {
			Notification notification = Notification.builder()
				.setTitle(requestDto.getTitle())
				.setBody(requestDto.getBody())
				.setImage(requestDto.getImage())
				.build();

			Message message = Message.builder()
				.setToken(member.getFirebaseToken())
				.setNotification(notification)
				.build();

			firebaseMessaging.send(message);
		}
	}

	private Member getMemberInfo(String memberId, String uuid) {
		Member member = memberId == null ?
			memberRepository.findByUuid(uuid).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);

		if (!uuid.equals(member.getUuid())) {
			throw new DeviceNotMatchedException();
		}
		return member;
	}
}
