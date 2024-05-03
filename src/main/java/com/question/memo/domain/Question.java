package com.question.memo.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Question {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "question_seq")
	private Long questionSeq;
	private String question;
	private Long questionOrder;
}
