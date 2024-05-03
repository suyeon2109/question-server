package com.question.memo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.dto.member.GuestCreateDto;
import com.question.memo.dto.member.MemberCreateDto;
import com.question.memo.dto.member.MemberEditDto;
import com.question.memo.repository.MemberRepository;
import com.question.memo.util.AesUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	public String signup(MemberCreateDto dto) {
		Optional<Member> findByUuid = memberRepository.findByUuid(dto.getUuid());
		Optional<Member> findByMemberId = memberRepository.findByMemberId(dto.getMemberId());

		if (findByMemberId.isEmpty()) {
			if (findByUuid.isEmpty()) {
				memberRepository.save(Member.builder()
					.memberId(dto.getMemberId())
					.nickname(dto.getNickname())
					.guestYn("N")
					.uuid(dto.getUuid())
					.build());
			} else {
				findByUuid.get().editMemberInfo(MemberEditDto.builder()
					.memberId(dto.getMemberId())
					.nickname(dto.getNickname())
					.guestYn("N")
					.uuid(dto.getUuid())
					.build());
			}
			return dto.getMemberId();
		} else {
			throw new IllegalStateException("이미 가입된 아이디 입니다.");
		}
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
}
