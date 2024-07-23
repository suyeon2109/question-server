package com.question.memo.exception;

public class SignUpRequiredException extends RuntimeException {
	private static final String message = "회원가입 필요";

	public SignUpRequiredException() {
		super(message);
	}
	public int getStatusCode() {
		return 464;
	}
}
