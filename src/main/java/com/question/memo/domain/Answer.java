package com.question.memo.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Answer {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long answerSeq;

	@Column(columnDefinition = "text")
	private String answer;

	private LocalDateTime answerDate;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "question_seq")
	private Question question;
}
