package com.question.memo.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Badge {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "badge_seq")
	private Long badgeSeq;
	private String badge;
}