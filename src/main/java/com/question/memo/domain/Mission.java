package com.question.memo.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Mission {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long missionSeq;
	private String mission;
	private String missionOrder;
}
