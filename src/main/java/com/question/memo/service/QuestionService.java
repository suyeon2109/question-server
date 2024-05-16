package com.question.memo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.domain.MemberUsedQuestion;
import com.question.memo.domain.Question;
import com.question.memo.dto.question.QuestionRequestDto;
import com.question.memo.dto.question.QuestionResponseDto;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.exception.QuestionNotRemainException;
import com.question.memo.repository.MemberRepository;
import com.question.memo.repository.MemberUsedQuestionRepository;
import com.question.memo.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
	private final MemberRepository memberRepository;
	private final QuestionRepository questionRepository;
	private final MemberUsedQuestionRepository memberUsedQuestionRepository;

	public QuestionResponseDto getQuestion(QuestionRequestDto dto) {
		Member member = dto.getMemberId() == null ?
			memberRepository.findByUuid(dto.getUuid()).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(dto.getMemberId()).orElseThrow(MemberNotFoundException::new);

		if (!dto.getUuid().equals(member.getUuid())) {
			member.editUuid(dto.getUuid());
		}

		if (member.getLastQuestionDate() == null || !LocalDate.now().isEqual(member.getLastQuestionDate())) {
			Optional<Question> question = questionRepository.findByRandom(member);
			question.ifPresent(q -> {
				member.editQuestion(q);
				saveUsedQuestion(member);
			});
		}

		return Optional.ofNullable(member.getQuestion())
			.map(q -> QuestionResponseDto.builder()
				.questionSeq(q.getQuestionSeq())
				.question(q.getQuestion())
				.questionOrder(q.getQuestionOrder())
				.build())
			.orElseThrow(QuestionNotRemainException::new);
	}
	private void saveUsedQuestion(Member member) {
		memberUsedQuestionRepository.save(MemberUsedQuestion.builder()
			.usedDate(LocalDate.now())
			.member(member)
			.question(member.getQuestion())
			.build());
	}
}
