package com.question.memo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.dto.mission.MissionRequestDto;
import com.question.memo.dto.mission.MissionResponseDto;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.repository.MemberRepository;
import com.question.memo.repository.MissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {
	private final MissionRepository missionRepository;
	private final MemberRepository memberRepository;

	public List<MissionResponseDto> getMissionList(MissionRequestDto dto) {
		Member member = memberRepository.findByMemberId(dto.getMemberId()).orElseThrow(MemberNotFoundException::new);
		if (!dto.getUuid().equals(member.getUuid())) {
			member.editUuid(dto.getUuid());
		}

		return missionRepository.getMissionList(member.getMemberSeq());
	}
}
