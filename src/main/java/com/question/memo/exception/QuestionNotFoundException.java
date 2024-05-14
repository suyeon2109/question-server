package com.question.memo.exception;

public class QuestionNotFoundException extends RuntimeException {
	private static final String message = "질문 정보 없음";

	public QuestionNotFoundException() {
		super(message);
	}
	public int getStatusCode() {
		return 462;
	}
}
