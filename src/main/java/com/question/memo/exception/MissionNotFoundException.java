package com.question.memo.exception;

public class MissionNotFoundException extends RuntimeException {
	private static final String message = "미션 정보 없음";

	public MissionNotFoundException() {
		super(message);
	}
	public int getStatusCode() {
		return 465;
	}
}
