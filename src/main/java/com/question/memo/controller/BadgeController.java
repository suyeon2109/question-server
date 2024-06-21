package com.question.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.badge.BadgeCreateDto;
import com.question.memo.dto.badge.BadgeRequestDto;
import com.question.memo.dto.badge.BadgeResponseDto;
import com.question.memo.dto.member.MemberRequestDto;
import com.question.memo.service.BadgeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/badges")
public class BadgeController {
	private final BadgeService badgeService;

	@GetMapping
	public CommonResponse<List<BadgeResponseDto>> getMissionList(@Valid BadgeRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		List<BadgeResponseDto> list = badgeService.getBadgeList(dto);

		CommonResponse<List<BadgeResponseDto>> response = CommonResponse.<List<BadgeResponseDto>>builder()
			.code(HttpStatus.OK.value())
			.message("획득한 뱃지 리스트 조회")
			.payload(list)
			.build();
		return response;
	}

	@GetMapping("/{badgeSeq}")
	public CommonResponse<BadgeResponseDto> getBadgeInfo(@PathVariable Long badgeSeq, @Valid MemberRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		BadgeResponseDto badgeInfo = badgeService.getBadgeInfo(badgeSeq, dto);

		CommonResponse<BadgeResponseDto> response = CommonResponse.<BadgeResponseDto>builder()
			.code(HttpStatus.OK.value())
			.message("뱃지 상세 조회")
			.payload(badgeInfo)
			.build();
		return response;
	}

	@PostMapping("/{badgeSeq}")
	public CommonResponse<BadgeResponseDto> receiveBadge(@PathVariable Long badgeSeq, @Valid @RequestBody MemberRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		badgeService.receiveBadge(badgeSeq, dto);

		CommonResponse<BadgeResponseDto> response = CommonResponse.<BadgeResponseDto>builder()
			.code(HttpStatus.OK.value())
			.message("뱃지 획득")
			.build();
		return response;
	}

	@PostMapping
	public CommonResponse<String> saveMission(@Valid @RequestBody BadgeCreateDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		badgeService.saveBadge(dto);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("뱃지가 등록되었습니다.")
			.build();
		return response;
	}

	@PutMapping("/{badgeSeq}")
	public CommonResponse<String> applyBadge(@PathVariable Long badgeSeq, @Valid @RequestBody MemberRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		badgeService.applyBadge(badgeSeq, dto);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("뱃지가 적용되었습니다.")
			.build();
		return response;
	}

}
