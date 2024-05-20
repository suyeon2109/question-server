package com.question.memo.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private String email;
	private String ageRange;
	private String guestYn;
	private String uuid;
	private LocalDateTime createdAt;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "last_question_id", referencedColumnName = "question_seq")
	private Question question;
	private LocalDate lastQuestionDate;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "badge_id", referencedColumnName = "badge_seq")
	private Badge badge;
	private LocalDateTime badgeDate;

	public void editMemberInfo(MemberEditDto dto) {
		this.memberId = dto.getMemberId();
		this.nickname = dto.getNickname();
		this.email = dto.getEmail();
		this.ageRange = dto.getAgeRange();
		this.guestYn = dto.getGuestYn();
		this.uuid = dto.getUuid();
		this.createdAt = dto.getCreatedAt();
	}

	public void editUuid(String uuid) {
		this.uuid = uuid;
	}

	public void editQuestion(Question question) {
		this.question = question;
		this.lastQuestionDate = LocalDate.now();
	}

	public void editBadge(Badge badge) {
		this.badge = badge;
		this.badgeDate = LocalDateTime.now();
	}
}
