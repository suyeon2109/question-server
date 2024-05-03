package com.question.memo.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDate;

import com.question.memo.dto.member.MemberEditDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long memberSeq;
	private String memberId;
	private String nickname;
	private String guestYn;
	private String uuid;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "last_question_id", referencedColumnName = "question_seq")
	private Question question;
	private LocalDate lastQuestionDate;

	public void editMemberInfo(MemberEditDto dto) {
		this.memberId = dto.getMemberId();
		this.nickname = dto.getNickname();
		this.guestYn = dto.getGuestYn();
		this.uuid = dto.getUuid();
	}

	public void editQuestion(Question question, LocalDate lastQuestionDate) {
		this.question = question;
		this.lastQuestionDate = lastQuestionDate;
	}
}
