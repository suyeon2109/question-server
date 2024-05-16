package com.question.memo.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.time.LocalDateTime;

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
@NoArgsConstructor
@AllArgsConstructor
public class MemberCompletedMission {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long memberCompleteQuestionSeq;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_seq")
	private Member member;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "mission_seq")
	private Mission mission;

	private LocalDateTime completedAt;
}
