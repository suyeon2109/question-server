package com.question.memo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.answer.AnswerRequestDto;
import com.question.memo.dto.answer.AnswerResponseDto;
import com.question.memo.service.AnswerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {
	private final AnswerService answerService;

	@PostMapping
	public CommonResponse<String> saveAnswer(@Valid @RequestBody AnswerRequestDto request, BindingResult e, @RequestHeader("Authorization") String token) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		answerService.saveAnswer(token, request);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("답변을 저장했습니다.")
			.build();
		return response;
	}

	@GetMapping
	public CommonResponse<List<AnswerResponseDto>> getAnswerList(@RequestHeader("Authorization") String token) {
		List<AnswerResponseDto> list = answerService.getAnswerList(token);

		CommonResponse<List<AnswerResponseDto>> response = CommonResponse.<List<AnswerResponseDto>>builder()
			.code(HttpStatus.OK.value())
			.message("답변 리스트 조회")
			.payload(list)
			.build();
		return response;
	}

	@GetMapping("/{answerSeq}")
	public CommonResponse<AnswerResponseDto> getAnswer(@PathVariable Long answerSeq) {
		AnswerResponseDto dto = answerService.getAnswer(answerSeq);

		CommonResponse<AnswerResponseDto> response = CommonResponse.<AnswerResponseDto>builder()
			.code(HttpStatus.OK.value())
			.message("답변 상세 조회")
			.payload(dto)
			.build();
		return response;
	}
}
