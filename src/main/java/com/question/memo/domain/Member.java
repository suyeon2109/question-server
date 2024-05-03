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
public class Member {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long memberSeq;
	private String memberId;
	private String guestYn;
	private String uuid;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "last_question_id", referencedColumnName = "question_seq")
	private Question question;
	private LocalDate lastQuestionDate;
}
