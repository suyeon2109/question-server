package com.question.memo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
