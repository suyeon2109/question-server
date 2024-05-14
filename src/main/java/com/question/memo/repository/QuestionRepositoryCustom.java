package com.question.memo.repository;

import java.util.Optional;

import com.question.memo.domain.Question;

public interface QuestionRepositoryCustom {
	Optional<Question> findByRandom(Long memberSeq);
}
