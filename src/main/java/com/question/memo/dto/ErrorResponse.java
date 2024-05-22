package com.question.memo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ErrorResponse {
    private int code;
    private String message;
    private String description;
}