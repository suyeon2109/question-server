package com.question.memo.exception;

public class MemberNotFoundException extends RuntimeException {
	private static final String message = "유저 정보 없음";

	public MemberNotFoundException() {
		super(message);
	}
	public int getStatusCode() {
		return 600;
	}
}
