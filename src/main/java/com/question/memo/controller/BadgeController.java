package com.question.memo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.badge.BadgeResponseDto;
import com.question.memo.dto.member.MemberRequestDto;
import com.question.memo.service.BadgeService;
import com.question.memo.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/badges")
public class BadgeController {
	private final BadgeService badgeService;
	private final MemberService memberService;
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
	public CommonResponse<String> applyBadge(@PathVariable Long badgeSeq, @Valid @RequestBody MemberRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		} else {
			badgeService.applyBadge(badgeSeq, dto);
			CommonResponse<String> response = CommonResponse.<String>builder()
				.code(HttpStatus.OK.value())
				.message("뱃지가 적용되었습니다.")
				.build();
			return response;
		}
	}

}
