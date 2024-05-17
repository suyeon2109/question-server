package com.question.memo.repository.mission;

import static com.question.memo.domain.QBadge.*;
import static com.question.memo.domain.QMemberCompletedMission.*;
import static com.question.memo.domain.QMission.*;
import static com.question.memo.domain.QMissionBadge.*;

import java.util.List;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.question.memo.dto.mission.MissionResponseDto;
import com.question.memo.dto.mission.QMissionResponseDto;
import com.question.memo.repository.mission.MissionRepositoryCustom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	@Override
	public List<MissionResponseDto> getMissionList(Long memberSeq) {
		return jpaQueryFactory.select(
				new QMissionResponseDto(mission1.missionSeq, mission1.mission, mission1.description,
					badge1.badgeSeq, badge1.badge, badge1.description,
					new CaseBuilder().when(memberCompletedMission.mission.missionSeq.isNotNull())
						.then("Y")
						.otherwise("N")
						.as("completedYn"), memberCompletedMission.completedAt))
			.from(mission1)
			.leftJoin(memberCompletedMission)
			.on(mission1.missionSeq.eq(memberCompletedMission.mission.missionSeq)
				.and(memberCompletedMission.member.memberSeq.eq(memberSeq)))
			.leftJoin(missionBadge)
			.on(mission1.missionSeq.eq(missionBadge.mission.missionSeq))
			.leftJoin(badge1)
			.on(missionBadge.badge.badgeSeq.eq(badge1.badgeSeq))
			.orderBy(mission1.missionOrder.asc())
			.fetch();
	}
}
