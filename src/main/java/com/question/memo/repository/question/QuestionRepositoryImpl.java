package com.question.memo.repository.question;

import static com.question.memo.domain.QMemberUsedQuestion.*;
import static com.question.memo.domain.QQuestion.*;

import java.util.Optional;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.question.memo.domain.Member;
import com.question.memo.domain.Question;
import com.question.memo.repository.question.QuestionRepositoryCustom;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	@Override
	public Optional<Question> findByRandom(Member member) {
		return Optional.ofNullable(jpaQueryFactory.select(question1)
			.from(question1)
			.where(question1.questionSeq.notIn(jpaQueryFactory.select(memberUsedQuestion.question.questionSeq)
				.from(memberUsedQuestion)
				.where(memberSeqEq(member.getMemberSeq()))),
				guestYnEq(member.getGuestYn()))
			.orderBy(question1.questionOrder.asc().nullsLast(),
				Expressions.numberTemplate(Double.class, "function('rand')").asc())
			.limit(1)
			.fetchOne());
	}

	private Predicate guestYnEq(String guestYn) {
		return "N".equals(guestYn) ? null : question1.guestYn.eq(guestYn);
	}

	private Predicate memberSeqEq(Long memberSeq) {
		return memberSeq == null ? null : memberUsedQuestion.member.memberSeq.eq(memberSeq);
	}
}
