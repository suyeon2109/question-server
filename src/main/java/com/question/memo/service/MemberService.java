package com.question.memo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.dto.member.GuestCreateDto;
import com.question.memo.dto.member.MemberCreateDto;
import com.question.memo.dto.member.MemberEditDto;
import com.question.memo.dto.member.MemberLoginDto;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.repository.MemberRepository;

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
				.build());
		}
	}

	public void isDuplicatedNickname(String nickname) {
		Optional<Member> byNickname = memberRepository.findByNickname(nickname);
		if (byNickname.isPresent()) {
			throw new IllegalStateException("이미 등록된 닉네임 입니다.");
		}
	}

	public Member login(MemberLoginDto dto) {
		Member member = dto.getMemberId() == null ?
			memberRepository.findByUuid(dto.getUuid()).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(dto.getMemberId()).orElseThrow(MemberNotFoundException::new);

		if (!dto.getUuid().equals(member.getUuid())) {
			member.editUuid(dto.getUuid());
		}

		return member;
	}
}
