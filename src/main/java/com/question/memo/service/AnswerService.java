package com.question.memo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.question.memo.domain.Answer;
import com.question.memo.domain.Member;
import com.question.memo.domain.Question;
import com.question.memo.dto.answer.AnswerListRequestDto;
import com.question.memo.dto.answer.AnswerRequestDto;
import com.question.memo.dto.answer.AnswerResponseDto;
import com.question.memo.exception.AnswerNotFoundException;
import com.question.memo.exception.DeviceNotMatchedException;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.exception.QuestionNotFoundException;
import com.question.memo.exception.SignUpRequiredException;
import com.question.memo.repository.answer.AnswerRepository;
import com.question.memo.repository.member.MemberRepository;
import com.question.memo.repository.question.QuestionRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	private final MemberRepository memberRepository;
	private final QuestionRepository questionRepository;
	public void saveAnswer(AnswerRequestDto dto) {
		Member member = getMember(dto.getMemberId(), dto.getUuid());

		Question question = questionRepository.findById(dto.getQuestionSeq())
			.orElseThrow(QuestionNotFoundException::new);

		answerRepository.save(Answer.builder()
			.answer(dto.getAnswer())
			.answerDate(LocalDateTime.now())
			.member(member)
			.question(question)
			.build());
	}

	@Transactional(readOnly = true)
	public List<AnswerResponseDto> getAnswerList(AnswerListRequestDto dto) {
		Member member = getMember(dto.getMemberId(), dto.getUuid());
		List<Answer> list = answerRepository.findByMember(member).orElseThrow(AnswerNotFoundException::new);

		List<AnswerResponseDto> response = new ArrayList<>();

		list.forEach(a -> {
			AnswerResponseDto build = AnswerResponseDto.builder()
				.answerSeq(a.getAnswerSeq())
				.answer(a.getAnswer())
				.answerDate(a.getAnswerDate())
				.questionSeq(a.getQuestion().getQuestionSeq())
				.question(a.getQuestion().getQuestion())
				.nickname(a.getMember().getNickname())
				.badge(a.getMember().getBadge().getBadge())
				.build();
			response.add(build);
		});
		return response;
	}

	private Member getMember(String memberId, String uuid) {
		Member member = memberId == null ?
			memberRepository.findByUuid(uuid).orElseThrow(MemberNotFoundException::new) :
			memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);

		if (!uuid.equals(member.getUuid())) {
			throw new DeviceNotMatchedException();
		}
		return member;
	}

	@Transactional(readOnly = true)
	public AnswerResponseDto getAnswer(Long answerSeq) {
		Answer answer = answerRepository.findById(answerSeq).orElseThrow(AnswerNotFoundException::new);
		Member member = memberRepository.findById(answer.getMember().getMemberSeq())
			.orElseThrow(MemberNotFoundException::new);

		return AnswerResponseDto.builder()
			.answerSeq(answerSeq)
			.answer(answer.getAnswer())
			.answerDate(answer.getAnswerDate())
			.questionSeq(answer.getQuestion() == null ? null : answer.getQuestion().getQuestionSeq())
			.question(answer.getQuestion() == null ? null : answer.getQuestion().getQuestion())
			.nickname(member.getNickname())
			.badge(member.getBadge() == null ? null : member.getBadge().getBadge())
			.stickerYn(member.getStickerYn())
			.build();
	}

	public Long checkGuestRemainDays(AnswerResponseDto answer) {
		if (answer == null || !isAnswerWithinLastThreeDays(answer)) {
			return null;
		}

		Long daysDiff = ChronoUnit.DAYS.between(answer.getAnswerDate().toLocalDate(), LocalDate.now());
		if (daysDiff >= 5L) {
			throw new SignUpRequiredException();
		}

		return calculateRemainingDays(daysDiff);
	}

	private boolean isAnswerWithinLastThreeDays(AnswerResponseDto answer) {
		LocalDate answerDate = answer.getAnswerDate().toLocalDate();
		LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
		return answerDate.isEqual(threeDaysAgo) || answerDate.isBefore(threeDaysAgo);
	}

	private Long calculateRemainingDays(Long daysDiff) {
		return Math.max(0L, 5L - daysDiff);
	}

	@Transactional(readOnly = true)
	public AnswerResponseDto getFirstAnswer(@Valid String uuid) {
		Member member = getMember(null, uuid);
		Answer answer = answerRepository.findFirstByMemberOrderByAnswerDateAsc(member)
			.orElseThrow(AnswerNotFoundException::new);

		return AnswerResponseDto.builder()
			.answerSeq(answer.getAnswerSeq())
			.answer(answer.getAnswer())
			.answerDate(answer.getAnswerDate())
			.questionSeq(answer.getQuestion() == null ? null : answer.getQuestion().getQuestionSeq())
			.question(answer.getQuestion() == null ? null : answer.getQuestion().getQuestion())
			.nickname(member.getNickname())
			.badge(member.getBadge() == null ? null : member.getBadge().getBadge())
			.build();
	}
}
