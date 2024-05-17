package com.question.memo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Badge;
import com.question.memo.domain.Member;
import com.question.memo.domain.Mission;
import com.question.memo.domain.MissionBadge;
import com.question.memo.dto.mission.MissionCreateDto;
import com.question.memo.dto.mission.MissionRequestDto;
import com.question.memo.dto.mission.MissionResponseDto;
import com.question.memo.exception.BadgeNotFoundException;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.exception.MissionNotFoundException;
import com.question.memo.repository.badge.BadgeRepository;
import com.question.memo.repository.member.MemberRepository;
import com.question.memo.repository.mission.MissionBadgeRepository;
import com.question.memo.repository.mission.MissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MissionService {
	private final MissionRepository missionRepository;
	private final MemberRepository memberRepository;
	private final MissionBadgeRepository missionBadgeRepository;
	private final BadgeRepository badgeRepository;

	public List<MissionResponseDto> getMissionList(MissionRequestDto dto) {
		Member member = dto.getMemberId() == null ?
			memberRepository.findByUuid(dto.getUuid()).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(dto.getMemberId()).orElseThrow(MemberNotFoundException::new);

		if (!dto.getUuid().equals(member.getUuid())) {
			member.editUuid(dto.getUuid());
		}

		return missionRepository.getMissionList(member.getMemberSeq());
	}

	public Long saveMission(MissionCreateDto dto) {
		Mission mission = missionRepository.save(Mission.builder()
			.mission(dto.getMission())
			.description(dto.getMissionDescription())
			.missionOrder(dto.getMissionOrder())
			.build());
		return mission.getMissionSeq();
	}

	public void saveMissionBadge(Long missionSeq, Long badgeSeq) {
		Mission mission = missionRepository.findById(missionSeq).orElseThrow(MissionNotFoundException::new);
		Badge badge = badgeRepository.findById(badgeSeq).orElseThrow(BadgeNotFoundException::new);

		missionBadgeRepository.save(MissionBadge.builder()
			.mission(mission)
			.badge(badge)
			.build());
	}
}
