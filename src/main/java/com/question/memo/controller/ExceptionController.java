package com.question.memo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.google.api.Http;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.question.memo.dto.ErrorResponse;
import com.question.memo.exception.AnswerNotFoundException;
import com.question.memo.exception.BadgeNotFoundException;
import com.question.memo.exception.BadgeNotReceivedException;
import com.question.memo.exception.DeviceNotMatchedException;
import com.question.memo.exception.MemberNotFoundException;
import com.question.memo.exception.MissionNotFoundException;
import com.question.memo.exception.QuestionNotFoundException;
import com.question.memo.exception.QuestionNotRemainException;
import com.question.memo.exception.SignUpRequiredException;

import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class ExceptionController extends RuntimeException {
    @ExceptionHandler(IllegalStateException.class)
    // @ApiResponse(responseCode = "409", description ="이미 가입된 회원")
    public ResponseEntity<ErrorResponse> illegalStateException(Exception ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    // @ApiResponse(responseCode = "400", description ="잘못된 파라미터")
    public ResponseEntity<ErrorResponse> illegalArgumentException(Exception ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    // @ApiResponse(responseCode = "460", description ="로그인 유저 정보 없음")
    public ResponseEntity<ErrorResponse> memberNotFoundException(MemberNotFoundException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ex.getStatusCode())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(QuestionNotRemainException.class)
    // @ApiResponse(responseCode = "461", description ="남은 질문 없음")
    public ResponseEntity<ErrorResponse> questionNotRemainException(QuestionNotRemainException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    // @ApiResponse(responseCode = "462", description ="질문 정보 없음")
    public ResponseEntity<ErrorResponse> questionNotFoundException(QuestionNotFoundException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    // @ApiResponse(responseCode = "463", description ="답변 리스트 없음")
    public ResponseEntity<ErrorResponse> answerNotFoundException(AnswerNotFoundException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(SignUpRequiredException.class)
    // @ApiResponse(responseCode = "464", description ="회원가입 필요")
    public ResponseEntity<ErrorResponse> signUpRequiredException(SignUpRequiredException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MissionNotFoundException.class)
    // @ApiResponse(responseCode = "465", description ="미션 정보 없음")
    public ResponseEntity<ErrorResponse> missionNotFoundException(MissionNotFoundException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BadgeNotFoundException.class)
    // @ApiResponse(responseCode = "466", description ="뱃지 정보 없음")
    public ResponseEntity<ErrorResponse> badgeNotFoundException(BadgeNotFoundException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BadgeNotReceivedException.class)
    // @ApiResponse(responseCode = "467", description ="뱃지 미획득")
    public ResponseEntity<ErrorResponse> badgeNotReceivedException(BadgeNotReceivedException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(FirebaseMessagingException.class)
    // @ApiResponse(responseCode = "468", description ="푸시알림 전송실패")
    public ResponseEntity<ErrorResponse> firebaseMessagingException(FirebaseMessagingException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(468)
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }

    @ExceptionHandler(DeviceNotMatchedException.class)
    // @ApiResponse(responseCode = "470", description ="기기 정보 불일치")
    public ResponseEntity<ErrorResponse> deviceNotMatchedException(DeviceNotMatchedException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(ex.getStatusCode())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }


    @ExceptionHandler(JwtException.class)
    // @ApiResponse(responseCode = "401", description ="JWT 이상")
    public ResponseEntity<ErrorResponse> jwtException(JwtException ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
            .code(HttpStatus.UNAUTHORIZED.value())
            .message(ex.getMessage())
            .description(request.getDescription(false))
            .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.OK);
    }
}
