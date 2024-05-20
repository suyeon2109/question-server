package com.question.memo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Badge;
import com.question.memo.domain.Member;
import com.question.memo.domain.MemberReceivedBadge;
import com.question.memo.dto.badge.BadgeResponseDto;
import com.question.memo.dto.member.MemberRequestDto;
import com.question.memo.dto.mission.MissionCreateDto;
import com.question.memo.exception.BadgeNotFoundException;
import com.question.memo.exception.BadgeNotReceivedException;
import com.question.memo.exception.DeviceNotMatchedException;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.repository.badge.BadgeRepository;
import com.question.memo.repository.badge.MemberReceivedBadgeRepository;
import com.question.memo.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BadgeService {
	private final BadgeRepository badgeRepository;
	private final MemberReceivedBadgeRepository memberReceivedBadgeRepository;
	private final MemberRepository memberRepository;
	public Long saveBadge(MissionCreateDto dto) {
		Badge badge = badgeRepository.save(Badge.builder()
			.badge(dto.getBadge())
			.description(dto.getBadgeDescription())
			.badgeOrder(dto.getBadgeOrder())
			.build());
		return badge.getBadgeSeq();
	}

	@Transactional(readOnly = true)
	public BadgeResponseDto getBadgeInfo(Long badgeSeq, MemberRequestDto dto) {
		Member member = getMemberInfo(dto.getMemberId(), dto.getUuid());
		Badge badge = badgeRepository.findById(badgeSeq).orElseThrow(BadgeNotFoundException::new);
		MemberReceivedBadge memberReceivedBadge = memberReceivedBadgeRepository.findByMemberAndBadge(member, badge)
			.orElseThrow(BadgeNotReceivedException::new);

		return BadgeResponseDto.builder()
			.badgeSeq(badge.getBadgeSeq())
			.badge(badge.getBadge())
			.description(badge.getDescription())
			.badgeOrder(badge.getBadgeOrder())
			.completedAt(memberReceivedBadge.getReceivedAt().toLocalDate())
			.build();
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

	public void applyBadge(Long badgeSeq, MemberRequestDto dto) {
		Member member = getMemberInfo(dto.getMemberId(), dto.getUuid());
		Badge badge = badgeRepository.findById(badgeSeq).orElseThrow(BadgeNotFoundException::new);
		Optional<MemberReceivedBadge> receivedBadge = memberReceivedBadgeRepository.findByMemberAndBadge(member, badge);
		if (receivedBadge.isPresent()) {
			member.editBadge(badge);
		} else {
			throw new BadgeNotReceivedException();
		}
	}
}
