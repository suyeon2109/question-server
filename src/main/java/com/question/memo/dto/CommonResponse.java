package com.question.memo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CommonResponse<T> {
    private Integer code;
    private String message;
    private T payload;
}
