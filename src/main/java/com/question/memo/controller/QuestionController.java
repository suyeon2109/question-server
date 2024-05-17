package com.question.memo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.question.memo.dto.CommonResponse;
import com.question.memo.dto.question.QuestionCreateDto;
import com.question.memo.dto.question.QuestionRequestDto;
import com.question.memo.dto.question.QuestionResponseDto;
import com.question.memo.service.QuestionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
	private final QuestionService questionService;
	@GetMapping
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

	@PostMapping
	public CommonResponse<String> saveQuestion(@Valid @RequestBody QuestionCreateDto dto, BindingResult e) {
		if (e.hasErrors()) {
			throw new IllegalArgumentException(e.getFieldErrors().get(0).getField() + " is null");
		} else {
			questionService.saveQuestion(dto);
			CommonResponse<String> response = CommonResponse.<String>builder()
				.code(HttpStatus.OK.value())
				.message("질문이 등록되었습니다.")
				.build();
			return response;
		}
	}
}
