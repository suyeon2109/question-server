package com.question.memo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.dto.member.GuestCreateDto;
import com.question.memo.dto.member.MemberCreateDto;
import com.question.memo.dto.member.MemberEditDto;
import com.question.memo.dto.member.MemberRequestDto;
import com.question.memo.dto.member.MemberResponseDto;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public String signup(MemberCreateDto dto) {
		Optional<Member> findByUuid = memberRepository.findByUuid(dto.getUuid());
		Optional<Member> findByMemberId = memberRepository.findByMemberId(dto.getMemberId());

		if (findByMemberId.isEmpty() && findByUuid.isEmpty()) {
			createMember(dto);
			return dto.getMemberId();
		}

		if (findByUuid.isPresent()) {
			if (findByUuid.get().getMemberId() != null) {
				throw new IllegalStateException("이미 가입된 카카오 계정이 존재합니다.");
			}
			Member member = findByUuid.get();
			editMember(dto, member);
			return dto.getMemberId();
		}

		throw new IllegalStateException("이미 가입된 아이디 입니다.");
	}

	private static void editMember(MemberCreateDto dto, Member member) {
		member.editMemberInfo(MemberEditDto.builder()
			.memberId(dto.getMemberId())
			.nickname(dto.getNickname())
			.email(dto.getEmail())
			.ageRange(dto.getAgeRange())
			.guestYn("N")
			.uuid(dto.getUuid())
			.createdAt(dto.getCreatedAt())
			.build());
	}

	private void createMember(MemberCreateDto dto) {
		memberRepository.save(Member.builder()
			.memberId(dto.getMemberId())
			.nickname(dto.getNickname())
			.email(dto.getEmail())
			.ageRange(dto.getAgeRange())
			.guestYn("N")
			.uuid(dto.getUuid())
			.createdAt(LocalDateTime.now())
			.build());
	}

	public void saveGuest(GuestCreateDto dto) {
		Optional<Member> findByUuid = memberRepository.findByUuid(dto.getUuid());
		if (findByUuid.isPresent()) {
			throw new IllegalStateException("이미 등록된 게스트 입니다.");
		} else {
			memberRepository.save(Member.builder()
				.guestYn("Y")
				.uuid(dto.getUuid())
				.createdAt(LocalDateTime.now())
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

	public MemberResponseDto getMemberInfo(MemberRequestDto dto) {
		Member member = getMember(dto.getMemberId(), dto.getUuid());

		return MemberResponseDto.builder()
			.memberSeq(member.getMemberSeq())
			.memberId(member.getMemberId())
			.nickname(member.getNickname())
			.email(member.getEmail())
			.ageRange(member.getAgeRange())
			.guestYn(member.getGuestYn())
			.uuid(member.getUuid())
			.createdAt(member.getCreatedAt())
			.last_question_id(member.getQuestion() == null ? null : member.getQuestion().getQuestionSeq())
			.lastQuestionDate(member.getLastQuestionDate())
			.badge(member.getBadge() == null ? null : member.getBadge().getBadge())
			.badgeDate(member.getBadgeDate())
			.build();
	}

	private Member getMember(String memberId, String uuid) {
		Member member = memberId == null ?
			memberRepository.findByUuid(uuid).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);

		if (!uuid.equals(member.getUuid())) {
			member.editUuid(uuid);
		}
		return member;
	}
}
