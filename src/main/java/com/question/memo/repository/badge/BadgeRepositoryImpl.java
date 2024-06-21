package com.question.memo.repository.badge;

import static com.question.memo.domain.QBadge.*;
import static com.question.memo.domain.QMemberReceivedBadge.*;

import java.util.List;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.question.memo.dto.badge.BadgeResponseDto;
import com.question.memo.dto.badge.QBadgeResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BadgeRepositoryImpl implements BadgeRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;
	@Override
	public List<BadgeResponseDto> getBadgeList(Long memberSeq) {
		return jpaQueryFactory.select(
				new QBadgeResponseDto(badge1.badgeSeq, badge1.badge, badge1.description, badge1.requiredMissions,
					new CaseBuilder().when(memberReceivedBadge.badge.badgeSeq.isNotNull())
						.then("Y")
						.otherwise("N")
						.as("receivedYn"), memberReceivedBadge.receivedAt))
			.from(badge1)
			.leftJoin(memberReceivedBadge)
			.on(badge1.badgeSeq.eq(memberReceivedBadge.badge.badgeSeq)
				.and(memberReceivedBadge.member.memberSeq.eq(memberSeq)))
			.orderBy(badge1.badgeSeq.asc())
			.fetch();
	}
}
