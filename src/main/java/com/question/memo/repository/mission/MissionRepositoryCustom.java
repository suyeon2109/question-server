package com.question.memo.repository.mission;

import java.util.List;

import com.question.memo.dto.mission.MissionResponseDto;

public interface MissionRepositoryCustom {
	List<MissionResponseDto> getMissionList(Long memberSeq);

}
