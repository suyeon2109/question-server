package com.question.memo.exception;

public class AnswerNotFoundException extends RuntimeException {
	private static final String message = "답변 리스트 없음";

	public AnswerNotFoundException() {
		super(message);
	}
	public int getStatusCode() {
		return 463;
	}
}
