package com.question.memo.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class UserUsedQuestion {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long userUsedQuestionSeq;
	private LocalDate usedDate;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "question_seq")
	private Question question;
}
