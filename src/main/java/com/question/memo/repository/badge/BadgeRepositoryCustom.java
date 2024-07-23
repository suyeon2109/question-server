package com.question.memo.repository.badge;

import java.util.List;

import com.question.memo.dto.badge.BadgeResponseDto;

public interface BadgeRepositoryCustom {
	List<BadgeResponseDto> getBadgeList(Long memberSeq);
}
