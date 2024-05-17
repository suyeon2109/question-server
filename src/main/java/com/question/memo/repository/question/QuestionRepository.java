package com.question.memo.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
	// @Query(value = "SELECT * FROM question order by question_order is null asc, RAND() limit 1",nativeQuery = true)
	// Question findByRandom(@Param("usedQuestion") List<MemberUsedQuestion> list);
}
