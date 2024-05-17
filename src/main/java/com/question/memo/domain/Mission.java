package com.question.memo.domain;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long missionSeq;
	private String mission;
	private String description;
	private Long missionOrder;
}
