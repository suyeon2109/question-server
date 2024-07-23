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
@AllArgsConstructor
@NoArgsConstructor
public class Nickname {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long nicknameSeq;
	private String type;
	private String word;
}
