package com.question.memo.exception;

public class BadgeNotReceivedException extends RuntimeException {
	private static final String message = "뱃지 미획득";

	public BadgeNotReceivedException() {
		super(message);
	}
	public int getStatusCode() {
		return 467;
	}
}
