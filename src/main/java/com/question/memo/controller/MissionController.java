package com.question.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.mission.MissionRequestDto;
import com.question.memo.dto.mission.MissionResponseDto;
import com.question.memo.service.MissionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MissionController {
	private final MissionService missionService;
	@GetMapping("/missions")
	public CommonResponse<List<MissionResponseDto>> getMissionList(@Valid MissionRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		} else {
			List<MissionResponseDto> list = missionService.getMissionList(dto);

			CommonResponse<List<MissionResponseDto>> response = CommonResponse.<List<MissionResponseDto>>builder()
				.code(HttpStatus.OK.value())
				.message("미션 리스트 조회")
				.payload(list)
				.build();
			return response;
		}
	}
}
