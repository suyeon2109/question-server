package com.question.memo.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.question.memo.dto.ErrorResponse;

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

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    // @ApiResponse(responseCode = "400", description ="잘못된 파라미터")
    public ResponseEntity<ErrorResponse> illegalArgumentException(Exception ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }

    // @ExceptionHandler({PasswordNotEqual.class, MemberNotFound.class})
    // @ApiResponse(responseCode = "401", description ="인가되지 않은 사용자")
    // public ResponseEntity<ErrorResponse> memberNotFoundException(Exception ex, WebRequest request) {
    //     ErrorResponse response = ErrorResponse.builder()
    //             .code(HttpStatus.UNAUTHORIZED.value())
    //             .message(ex.getMessage())
    //             .description(request.getDescription(false))
    //             .build();
    //
    //     return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNAUTHORIZED);
    // }
    //
    // @ExceptionHandler(JwtException.class)
    // @ApiResponse(responseCode = "460", description ="잘못된 JWT")
    // public ResponseEntity<ErrorResponse> jwtException(Exception ex, WebRequest request) {
    //     ErrorResponse response = ErrorResponse.builder()
    //             .code(460)
    //             .message(ex.getMessage())
    //             .description(request.getDescription(false))
    //             .build();
    //
    //     return new ResponseEntity<ErrorResponse>(response, HttpStatus.FORBIDDEN);
    // }
}
