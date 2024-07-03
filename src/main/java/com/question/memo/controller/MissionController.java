package com.question.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.mission.MissionCreateDto;
import com.question.memo.dto.mission.MissionResponseDto;
import com.question.memo.service.MissionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/missions")
public class MissionController {
	private final MissionService missionService;

	@GetMapping
	public CommonResponse<List<MissionResponseDto>> getMissionList(@RequestHeader("Authorization") String token) {
		List<MissionResponseDto> list = missionService.getMissionList(token);

		CommonResponse<List<MissionResponseDto>> response = CommonResponse.<List<MissionResponseDto>>builder()
			.code(HttpStatus.OK.value())
			.message("미션 리스트 조회")
			.payload(list)
			.build();
		return response;
	}

	@PostMapping
	public CommonResponse<String> saveMission(@Valid @RequestBody MissionCreateDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		missionService.saveMission(dto);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("미션이 등록되었습니다.")
			.build();
		return response;
	}

}
