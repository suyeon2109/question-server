package com.question.memo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.question.QuestionRequestDto;
import com.question.memo.dto.question.QuestionResponseDto;
import com.question.memo.service.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class QuestionController {
	private final QuestionService questionService;
	@GetMapping("/questions")
	public CommonResponse<QuestionResponseDto> getQuestion(@Valid QuestionRequestDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		}

		QuestionResponseDto question = questionService.getQuestion(dto);

		CommonResponse<QuestionResponseDto> response = CommonResponse.<QuestionResponseDto>builder()
			.code(HttpStatus.OK.value())
			.message("오늘의 질문")
			.payload(question)
			.build();
		return response;
	}
}
