package com.question.memo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.answer.AnswerRequestDto;
import com.question.memo.service.AnswerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AnswerController {
	private final AnswerService answerService;
	@PostMapping("/answers")
	public CommonResponse<String> saveAnswer(@Valid @RequestBody AnswerRequestDto request, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}
		answerService.saveAnswer(request);

		CommonResponse<String> response = CommonResponse.<String>builder()
			.code(HttpStatus.OK.value())
			.message("답변을 저장했습니다.")
			.build();
		return response;
	}
}
