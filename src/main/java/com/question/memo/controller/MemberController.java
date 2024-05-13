package com.question.memo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.domain.Member;
import com.question.memo.dto.member.GuestCreateDto;
import com.question.memo.dto.member.MemberCreateDto;
import com.question.memo.dto.member.MemberLoginDto;
import com.question.memo.service.MemberService;
import com.question.memo.dto.CommonResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/guests")
	public CommonResponse<String> saveGuest(@Valid @RequestBody GuestCreateDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		} else {
			memberService.saveGuest(dto);
			CommonResponse<String> response = CommonResponse.<String>builder()
				.code(HttpStatus.OK.value())
				.message("게스트로 등록되었습니다.")
				.build();
			return response;
		}
	}

	@GetMapping("/nickname")
	public CommonResponse<String> isDuplicatedNickname(String nickname) {
		String request = Optional.ofNullable(nickname).orElseThrow(() -> new IllegalArgumentException("nickname is null"));
		memberService.isDuplicatedNickname(request);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("사용 가능한 닉네임 입니다.")
			.payload(nickname)
			.build();
		return response;
	}

	@PostMapping("/members")
	public CommonResponse<String> signup(@Valid @RequestBody MemberCreateDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		} else {
			String userId = memberService.signup(dto);
			CommonResponse<String> response = CommonResponse.<String>builder()
				.code(HttpStatus.OK.value())
				.message("가입되었습니다.")
				.payload(userId)
				.build();
			return response;
		}
	}

	@PostMapping("/login")
	public CommonResponse<Map> login(@Valid @RequestBody MemberLoginDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		} else {
			Member member = memberService.login(dto);
			HashMap<String, Object> map = new HashMap<>();
			map.put("memberInfo", member);

			CommonResponse<Map> response = CommonResponse.<Map>builder()
				.code(HttpStatus.OK.value())
				.message("로그인 되었습니다.")
				.payload(map)
				.build();
			return response;
		}
	}
}
