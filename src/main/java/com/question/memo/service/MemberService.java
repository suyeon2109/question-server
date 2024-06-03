package com.question.memo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.dto.fcm.PushAlarm;
import com.question.memo.dto.member.GuestCreateDto;
import com.question.memo.dto.member.MemberAlarmsEditDto;
import com.question.memo.dto.member.MemberCreateDto;
import com.question.memo.dto.member.MemberEditDto;
import com.question.memo.dto.member.MemberFirebaseTokenEditDto;
import com.question.memo.dto.member.MemberLoginDto;
import com.question.memo.dto.member.MemberRequestDto;
import com.question.memo.dto.member.MemberResponseDto;
import com.question.memo.dto.member.MemberStickersEditDto;
import com.question.memo.exception.DeviceNotMatchedException;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.repository.member.MemberRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public String signup(MemberCreateDto dto) {
		Optional<Member> findByFcmToken = memberRepository.findByFirebaseToken(dto.getFirebaseToken());
		Optional<Member> findByMemberId = memberRepository.findByMemberId(dto.getMemberId());
		isDuplicatedNickname(dto.getNickname());

		if (findByMemberId.isPresent()) {
			throw new IllegalStateException("이미 가입된 아이디 입니다.");
		}

		findByFcmToken.ifPresentOrElse(member -> {
			if (member.getMemberId() != null) {
				throw new IllegalStateException("이미 가입된 카카오 계정이 존재합니다.");
			}
			editMember(dto, member);
		}, () -> createMember(dto));

		return dto.getMemberId();
	}

	private static void editMember(MemberCreateDto dto, Member member) {
		member.editMemberInfo(MemberEditDto.builder()
			.memberId(dto.getMemberId())
			.nickname(dto.getNickname())
			.email(dto.getEmail())
			.ageRange(dto.getAgeRange())
			.guestYn("N")
			.firebaseToken(dto.getFirebaseToken())
			.createdAt(dto.getCreatedAt())
			.stickerYn("N")
			.pushAlarm(PushAlarm.NONE)
			.build());
	}

	private void createMember(MemberCreateDto dto) {
		memberRepository.save(Member.builder()
			.memberId(dto.getMemberId())
			.nickname(dto.getNickname())
			.email(dto.getEmail())
			.ageRange(dto.getAgeRange())
			.guestYn("N")
			.firebaseToken(dto.getFirebaseToken())
			.createdAt(LocalDateTime.now())
			.stickerYn("N")
			.pushAlarm(PushAlarm.NONE)
			.build());
	}

	public void saveGuest(GuestCreateDto dto) {
		Optional<Member> findByFcmToken = memberRepository.findByFirebaseToken(dto.getFirebaseToken());
		if (findByFcmToken.isPresent()) {
			throw new IllegalStateException("이미 등록된 게스트 입니다.");
		} else {
			memberRepository.save(Member.builder()
				.guestYn("Y")
				.firebaseToken(dto.getFirebaseToken())
				.createdAt(LocalDateTime.now())
				.pushAlarm(PushAlarm.NONE)
				.build());
		}
	}

	@Transactional(readOnly = true)
	public void isDuplicatedNickname(String nickname) {
		Optional<Member> byNickname = memberRepository.findByNickname(nickname);
		if (byNickname.isPresent()) {
			throw new IllegalStateException("이미 등록된 닉네임 입니다.");
		}
	}

	public MemberResponseDto login(@Valid MemberLoginDto dto) {
		Member member = dto.getMemberId() == null ?
			memberRepository.findByFirebaseToken(dto.getFirebaseToken()).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(dto.getMemberId()).orElseThrow(MemberNotFoundException::new);

		if (!dto.getFirebaseToken().equals(member.getFirebaseToken())) {
			member.editFirebaseToken(dto.getFirebaseToken());
		}

		return getMemberResponseDto(member);
	}

	public MemberResponseDto getMemberInfo(MemberRequestDto dto) {
		Member member = getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		return getMemberResponseDto(member);
	}

	private MemberResponseDto getMemberResponseDto(Member member) {
		return MemberResponseDto.builder()
			.memberSeq(member.getMemberSeq())
			.memberId(member.getMemberId())
			.nickname(member.getNickname())
			.email(member.getEmail())
			.ageRange(member.getAgeRange())
			.guestYn(member.getGuestYn())
			.firebaseToken(member.getFirebaseToken())
			.createdAt(member.getCreatedAt())
			.lastQuestionId(member.getQuestion() == null ? null : member.getQuestion().getQuestionSeq())
			.lastQuestionDate(member.getLastQuestionDate())
			.badge(member.getBadge() == null ? null : member.getBadge().getBadge())
			.badgeDate(member.getBadgeDate())
			.stickerYn(member.getStickerYn())
			.pushAlarm(member.getPushAlarm())
			.build();
	}

	public Member getMemberInfo(String memberId, String firebaseToken) {
		Member member = memberId == null ?
			memberRepository.findByFirebaseToken(firebaseToken).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);

		if (!firebaseToken.equals(member.getFirebaseToken())) {
			throw new DeviceNotMatchedException();
		}
		return member;
	}

	public void setStickers(MemberStickersEditDto dto) {
		Member member = getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		member.editStickers(dto);
	}

	public void setAlarms(MemberAlarmsEditDto dto) {
		Member member = getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		member.editPushAlarm(dto);
	}

	public void setFirebaseTokens(MemberFirebaseTokenEditDto dto) {
		Member member = getMemberInfo(dto.getMemberId(), dto.getCurrentFirebaseToken());
		member.editFirebaseToken(dto.getNewFirebaseToken());
	}

	@Transactional(readOnly = true)
	public String getRandomNickname() {
		String randomNickname;
		Optional<Member> byNickname;
		do {
			randomNickname = memberRepository.findAdjective() + " " + memberRepository.findNoun();
			byNickname = memberRepository.findByNickname(randomNickname);
		} while (byNickname.isPresent());
		return randomNickname;
	}
}
