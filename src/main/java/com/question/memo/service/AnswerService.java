package com.question.memo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Answer;
import com.question.memo.domain.Member;
import com.question.memo.domain.Question;
import com.question.memo.dto.answer.AnswerRequestDto;
import com.question.memo.dto.answer.AnswerResponseDto;
import com.question.memo.exception.AnswerNotFoundException;
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

	public List<AnswerResponseDto> getAnswerList(String memberId) {
		Member member = memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);
		List<Answer> list = answerRepository.findByMember(member).orElseThrow(AnswerNotFoundException::new);
		List<AnswerResponseDto> response = new ArrayList<>();

		list.forEach(a -> {
			AnswerResponseDto build = AnswerResponseDto.builder()
				.answerSeq(a.getAnswerSeq())
				.answer(a.getAnswer())
				.answerDate(a.getAnswerDate())
				.memberId(memberId)
				.nickname(member.getNickname())
				.questionSeq(a.getQuestion().getQuestionSeq())
				.question(a.getQuestion().getQuestion())
				.build();
			response.add(build);
		});
		return response;
	}

	public AnswerResponseDto getAnswer(Long answerSeq) {
		Answer answer = answerRepository.findById(answerSeq).orElseThrow(AnswerNotFoundException::new);
		return AnswerResponseDto.builder()
			.answerSeq(answerSeq)
			.answer(answer.getAnswer())
			.answerDate(answer.getAnswerDate())
			.memberId(answer.getMember().getMemberId())
			.nickname(answer.getMember().getNickname())
			.questionSeq(answer.getQuestion().getQuestionSeq())
			.question(answer.getQuestion().getQuestion())
			.build();
	}
}
