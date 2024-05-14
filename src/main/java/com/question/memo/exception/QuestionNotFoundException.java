package com.question.memo.exception;

public class QuestionNotFoundException extends RuntimeException {
	private static final String message = "남은 질문 없음";

	public QuestionNotFoundException() {
		super(message);
	}
	public int getStatusCode() {
		return 700;
	}
}
