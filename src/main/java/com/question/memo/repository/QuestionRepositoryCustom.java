package com.question.memo.repository;

import com.question.memo.domain.Question;

public interface QuestionRepositoryCustom {
	Question findByRandom(Long memberSeq);
}
