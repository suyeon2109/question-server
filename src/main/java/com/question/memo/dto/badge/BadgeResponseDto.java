package com.question.memo.dto.badge;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeResponseDto {
	private Long badgeSeq;
	private String badge;
	private String description;
	private Long badgeOrder;
	private LocalDate completedAt;
}
