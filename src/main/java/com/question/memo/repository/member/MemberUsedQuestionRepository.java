package com.question.memo.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.MemberUsedQuestion;

public interface MemberUsedQuestionRepository extends JpaRepository<MemberUsedQuestion, Long> {
}
