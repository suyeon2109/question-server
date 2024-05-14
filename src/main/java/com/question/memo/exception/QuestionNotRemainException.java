package com.question.memo.exception;

public class QuestionNotRemainException extends RuntimeException {
	private static final String message = "남은 질문 없음";

	public QuestionNotRemainException() {
		super(message);
	}
	public int getStatusCode() {
		return 461;
	}
}
