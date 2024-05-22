package com.question.memo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.domain.MemberUsedQuestion;
import com.question.memo.domain.Question;
import com.question.memo.dto.question.QuestionCreateDto;
import com.question.memo.dto.question.QuestionRequestDto;
import com.question.memo.dto.question.QuestionResponseDto;
import com.question.memo.exception.DeviceNotMatchedException;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.exception.QuestionNotRemainException;
import com.question.memo.repository.answer.AnswerRepository;
import com.question.memo.repository.member.MemberRepository;
import com.question.memo.repository.member.MemberUsedQuestionRepository;
import com.question.memo.repository.question.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
	private final MemberRepository memberRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final MemberUsedQuestionRepository memberUsedQuestionRepository;

	public QuestionResponseDto getQuestion(QuestionRequestDto dto) {
		Member member = getMemberInfo(dto.getMemberId(), dto.getUuid());
		Long count = answerRepository.countByMember(member);

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
				.count(count + 1)
				.build())
			.orElseThrow(QuestionNotRemainException::new);
	}

	private Member getMemberInfo(String memberId, String uuid) {
		Member member = memberId == null ?
			memberRepository.findByUuid(uuid).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);

		if (!uuid.equals(member.getUuid())) {
			throw new DeviceNotMatchedException();
		}
		return member;
	}

	private void saveUsedQuestion(Member member) {
		memberUsedQuestionRepository.save(MemberUsedQuestion.builder()
			.usedDate(LocalDate.now())
			.member(member)
			.question(member.getQuestion())
			.build());
	}

	public void saveQuestion(QuestionCreateDto dto) {
		questionRepository.save(Question.builder()
			.question(dto.getQuestion())
			.questionOrder(dto.getQuestionOrder())
			.guestYn(dto.getGuestYn())
			.build());
	}
}
