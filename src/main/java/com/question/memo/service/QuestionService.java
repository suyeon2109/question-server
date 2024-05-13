package com.question.memo.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.domain.MemberUsedQuestion;
import com.question.memo.domain.Question;
import com.question.memo.dto.question.QuestionResponseDto;
import com.question.memo.exception.MemberNotFoundException;
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

	public QuestionResponseDto getQuestion(String memberId) {
		Member member = memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);
		if (member.getLastQuestionDate() == null || !LocalDate.now().isEqual(member.getLastQuestionDate())) {
			Question question = questionRepository.findByRandom(member.getMemberSeq());
			member.editQuestion(question);
			saveMemberUsedQuestion(member);
		}

		Question question = member.getQuestion();
		return QuestionResponseDto.builder()
			.questionSeq(question.getQuestionSeq())
			.question(question.getQuestion())
			.questionOrder(question.getQuestionOrder())
			.build();
	}

	private void saveMemberUsedQuestion(Member member) {
		memberUsedQuestionRepository.save(MemberUsedQuestion.builder()
			.usedDate(LocalDate.now())
			.member(member)
			.question(member.getQuestion())
			.build());
	}
}
