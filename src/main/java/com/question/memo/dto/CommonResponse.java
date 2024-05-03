package com.question.memo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse<T> {
    private Integer code;
    private String message;
    private T payload;
}
