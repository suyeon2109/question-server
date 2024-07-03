package com.question.memo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.domain.Mission;
import com.question.memo.dto.mission.MissionCreateDto;
import com.question.memo.dto.mission.MissionResponseDto;
import com.question.memo.repository.mission.MissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {
	private final MissionRepository missionRepository;
	private final MemberService memberService;

	@Transactional(readOnly = true)
	public List<MissionResponseDto> getMissionList(String token) {
		Member member = memberService.getMember(token);
		return missionRepository.getMissionList(member.getMemberSeq());
	}

	public void saveMission(MissionCreateDto dto) {
		missionRepository.save(Mission.builder()
			.mission(dto.getMission())
			.description(dto.getMissionDescription())
			.missionOrder(dto.getMissionOrder())
			.build());
	}
}
