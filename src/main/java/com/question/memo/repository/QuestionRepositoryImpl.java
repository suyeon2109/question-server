package com.question.memo.repository;

import static com.question.memo.domain.QMemberUsedQuestion.*;
import static com.question.memo.domain.QQuestion.*;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.question.memo.domain.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	@Override
	public Question findByRandom(Long memberSeq) {
		return jpaQueryFactory.select(question1)
			.from(question1)
			.where(question1.questionSeq.notIn(jpaQueryFactory.select(memberUsedQuestion.question.questionSeq).from(memberUsedQuestion).where(memberSeqEq(memberSeq))))
			.orderBy(question1.questionOrder.asc().nullsLast(), Expressions.numberTemplate(Double.class, "function('rand')").asc())
			.limit(1)
			.fetchOne();
	}

	private Predicate memberSeqEq(Long memberSeq) {
		return memberSeq == null ? null : memberUsedQuestion.member.memberSeq.eq(memberSeq);
	}
}
