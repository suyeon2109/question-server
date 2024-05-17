package com.question.memo.exception;

public class BadgeNotFoundException extends RuntimeException {
	private static final String message = "뱃지 정보 없음";

	public BadgeNotFoundException() {
		super(message);
	}
	public int getStatusCode() {
		return 466;
	}
}
