package com.question.memo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Member;
import com.question.memo.domain.MemberUsedQuestion;
import com.question.memo.domain.Question;
import com.question.memo.dto.question.QuestionCreateDto;
import com.question.memo.dto.question.QuestionReportDto;
import com.question.memo.dto.question.QuestionRequestDto;
import com.question.memo.dto.question.QuestionResponseDto;
import com.question.memo.exception.QuestionNotRemainException;
import com.question.memo.repository.answer.AnswerRepository;
import com.question.memo.repository.member.MemberUsedQuestionRepository;
import com.question.memo.repository.question.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	private final MemberUsedQuestionRepository memberUsedQuestionRepository;
	private final JavaMailSender javaMailSender;
	private final MemberService memberService;

	public QuestionResponseDto getQuestion(QuestionRequestDto dto) {
		Member member = memberService.getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
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

	public void reportQuestion(QuestionReportDto dto) {
		Member member = memberService.getMemberInfo(dto.getMemberId(), dto.getFirebaseToken());
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("❗사각사각 질문 제보");
		message.setTo("rawfish.go@gmail.com");
		message.setText("제보자 id : " + member.getMemberId() + "\n" +
			"제보자 fcmToken : " + member.getFirebaseToken() + "\n" +
			"제보자 닉네임 : " + member.getNickname() + "\n" +
			"질문 : "+ dto.getQuestion());

		javaMailSender.send(message);
	}
}
