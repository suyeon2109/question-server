package com.question.memo.repository.answer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.question.memo.domain.Answer;
import com.question.memo.domain.Member;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
	Optional<List<Answer>> findByMember(Member member);

	Optional<Answer> findFirstByMemberOrderByAnswerDateAsc(Member member);
}
