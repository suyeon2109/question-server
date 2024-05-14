package com.question.memo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Answer;
import com.question.memo.domain.Member;
import com.question.memo.domain.Question;
import com.question.memo.dto.answer.AnswerRequestDto;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.exception.QuestionNotFoundException;
import com.question.memo.repository.AnswerRepository;
import com.question.memo.repository.MemberRepository;
import com.question.memo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final MemberRepository memberRepository;
	private final QuestionRepository questionRepository;
	public void saveAnswer(AnswerRequestDto request) {
		Member member = memberRepository.findByMemberId(request.getMemberId())
			.orElseThrow(MemberNotFoundException::new);
		Question question = questionRepository.findById(request.getQuestionSeq())
			.orElseThrow(QuestionNotFoundException::new);

		answerRepository.save(Answer.builder()
			.answer(request.getAnswer())
			.answerDate(LocalDateTime.now())
			.member(member)
			.question(question)
			.build());
	}
}
