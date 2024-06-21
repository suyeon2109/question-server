package com.question.memo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Badge;
import com.question.memo.domain.Member;
import com.question.memo.domain.MemberCompletedMission;
import com.question.memo.domain.MemberReceivedBadge;
import com.question.memo.dto.badge.BadgeCreateDto;
import com.question.memo.dto.badge.BadgeRequestDto;
import com.question.memo.dto.badge.BadgeResponseDto;
import com.question.memo.dto.member.MemberRequestDto;
import com.question.memo.exception.BadgeNotFoundException;
import com.question.memo.exception.BadgeNotReceivedException;
import com.question.memo.repository.badge.BadgeRepository;
import com.question.memo.repository.badge.MemberReceivedBadgeRepository;
import com.question.memo.repository.mission.MemberCompletedMissionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BadgeService {
	private final BadgeRepository badgeRepository;
	private final MemberReceivedBadgeRepository memberReceivedBadgeRepository;
	private final MemberService memberService;
	private final MemberCompletedMissionRepository memberCompletedMissionRepository;

	public void saveBadge(BadgeCreateDto dto) {
		badgeRepository.save(Badge.builder()
			.badge(dto.getBadge())
			.description(dto.getBadgeDescription())
			.requiredMissions(dto.getRequiredMissions())
			.build());
	}

	@Transactional(readOnly = true)
	public BadgeResponseDto getBadgeInfo(Long badgeSeq, MemberRequestDto dto) {
		Member member = memberService.getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		Badge badge = badgeRepository.findById(badgeSeq).orElseThrow(BadgeNotFoundException::new);
		MemberReceivedBadge memberReceivedBadge = memberReceivedBadgeRepository.findByMemberAndBadge(member, badge)
			.orElseThrow(BadgeNotReceivedException::new);

		return BadgeResponseDto.builder()
			.badgeSeq(badge.getBadgeSeq())
			.badge(badge.getBadge())
			.description(badge.getDescription())
			.requiredMissions(badge.getRequiredMissions())
			.receivedYn(memberReceivedBadge.getReceivedAt() != null ? "Y" : "N")
			.receivedAt(memberReceivedBadge.getReceivedAt())
			.build();
	}

	public void applyBadge(Long badgeSeq, MemberRequestDto dto) {
		Member member = memberService.getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		Badge badge = badgeRepository.findById(badgeSeq).orElseThrow(BadgeNotFoundException::new);
		Optional<MemberReceivedBadge> receivedBadge = memberReceivedBadgeRepository.findByMemberAndBadge(member, badge);
		if (receivedBadge.isPresent()) {
			member.editBadge(badge);
		} else {
			throw new BadgeNotReceivedException();
		}
	}

	@Transactional(readOnly = true)
	public List<BadgeResponseDto> getBadgeList(BadgeRequestDto dto) {
		Member member = memberService.getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		List<BadgeResponseDto> badgeList = badgeRepository.getBadgeList(member.getMemberSeq());

		return badgeList.stream()
			.filter(v -> v.getReceivedYn().equals("Y"))
			.collect(Collectors.toList());
	}

	public void receiveBadge(Long badgeSeq, MemberRequestDto dto) {
		Member member = memberService.getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		Badge badge = badgeRepository.findById(badgeSeq).orElseThrow(BadgeNotFoundException::new);
		Optional<MemberReceivedBadge> receivedBadge = memberReceivedBadgeRepository.findByMemberAndBadge(member, badge);
		List<MemberCompletedMission> missions = memberCompletedMissionRepository.findByMember(member);

		if (receivedBadge.isPresent())
			throw new IllegalStateException("이미 획득한 뱃지 입니다");

		if (missions.size() == badge.getRequiredMissions()) {
			memberReceivedBadgeRepository.save(
				MemberReceivedBadge.builder()
					.receivedAt(LocalDateTime.now())
					.member(member)
					.badge(badge)
					.build());
		} else {
			throw new IllegalArgumentException("뱃지를 획득하기 위한 미션 성공 횟수가 부족합니다");
		}
	}
}
