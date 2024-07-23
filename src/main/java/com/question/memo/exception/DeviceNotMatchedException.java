package com.question.memo.exception;

public class DeviceNotMatchedException extends RuntimeException {
	private static final String message = "기기 정보 불일치";

	public DeviceNotMatchedException() {
		super(message);
	}
	public int getStatusCode() {
		return 470;
	}
}
