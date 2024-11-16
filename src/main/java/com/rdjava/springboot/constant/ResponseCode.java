package com.rdjava.springboot.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(HttpStatus.OK, 0, "success"),
    FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 100, "general failed"),
    NOT_EXISTED(HttpStatus.UNAUTHORIZED, 105, "object not existed");

    private final HttpStatus httpStatus;
    private final int code;
    private final String description;
}
