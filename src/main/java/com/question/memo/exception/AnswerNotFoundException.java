package com.question.memo.exception;

public class AnswerNotFoundException extends RuntimeException {
	private static final String message = "답변을 찾을 수 없음";

	public AnswerNotFoundException() {
		super(message);
	}
	public int getStatusCode() {
		return 463;
	}
}
