package com.question.memo.domain;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class MissionBadge {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long missionBadgeSeq;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "mission_seq")
	private Mission mission;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "badge_seq")
	private Badge badge;
}