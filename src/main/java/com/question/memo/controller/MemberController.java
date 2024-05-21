package com.question.memo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.answer.AnswerResponseDto;
import com.question.memo.dto.member.GuestCreateDto;
import com.question.memo.dto.member.MemberAlarmsEditDto;
import com.question.memo.dto.member.MemberCreateDto;
import com.question.memo.dto.member.MemberFirebaseTokenEditDto;
import com.question.memo.dto.member.MemberLoginDto;
import com.question.memo.dto.member.MemberRequestDto;
import com.question.memo.dto.member.MemberResponseDto;
import com.question.memo.dto.member.MemberStickersEditDto;
import com.question.memo.service.AnswerService;
import com.question.memo.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final AnswerService answerService;

	@PostMapping("/guests")
	public CommonResponse<String> saveGuest(@Valid @RequestBody GuestCreateDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		memberService.saveGuest(dto);
		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("게스트로 등록되었습니다.")
			.build();
		return response;
	}

	@GetMapping("/nicknames")
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
		}
		String userId = memberService.signup(dto);
		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("가입되었습니다.")
			.payload(userId)
			.build();
		return response;
	}

	@PostMapping("/login")
	public CommonResponse<Map> login(@Valid @RequestBody MemberLoginDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		MemberResponseDto member = memberService.login(dto);
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberInfo", member);

		CommonResponse<Map> response = CommonResponse.<Map>builder()
			.code(HttpStatus.OK.value())
			.message("로그인 되었습니다.")
			.payload(map)
			.build();
		return response;
	}

	@GetMapping("/memberInfo")
	public CommonResponse<Map> getMemberInfo(@Valid MemberRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		MemberResponseDto member = memberService.getMemberInfo(dto);
		HashMap<String, Object> map = new HashMap<>();
		map.put("memberInfo", member);

		CommonResponse<Map> response = CommonResponse.<Map>builder()
			.code(HttpStatus.OK.value())
			.message("회원정보 조회")
			.payload(map)
			.build();
		return response;
	}

	@GetMapping("/members/remainDays")
	public CommonResponse<Map<String, Object>> checkGuestRemainDays(String uuid) {
		String request = Optional.ofNullable(uuid).orElseThrow(() -> new IllegalArgumentException("uuid is null"));

		AnswerResponseDto answer = answerService.getFirstAnswer(request);
		Long remainDays = answerService.checkGuestRemainDays(answer);
		Map<String, Object> map = new HashMap<>();
		map.put("remainDays", remainDays);

		CommonResponse<Map<String, Object>> response = CommonResponse.<Map<String, Object>>builder()
			.code(HttpStatus.OK.value())
			.message("게스트 이용 잔여일 조회")
			.payload(map)
			.build();
		return response;
	}

	@PutMapping("/stickers")
	public CommonResponse<String> setStickers(@Valid @RequestBody MemberStickersEditDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		memberService.setStickers(dto);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("스티커 적용 여부가 업데이트 되었습니다.")
			.build();
		return response;
	}

	@PutMapping("/alarms")
	public CommonResponse<String> setPushAlarms(@Valid @RequestBody MemberAlarmsEditDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		memberService.setAlarms(dto);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("푸쉬알람이 업데이트 되었습니다.")
			.build();
		return response;
	}

	@PutMapping("/firebaseTokens")
	public CommonResponse<String> setFirebaseTokens(@Valid @RequestBody MemberFirebaseTokenEditDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		memberService.setFirebaseTokens(dto);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("firebase 토큰이 업데이트 되었습니다.")
			.build();
		return response;
	}
}
