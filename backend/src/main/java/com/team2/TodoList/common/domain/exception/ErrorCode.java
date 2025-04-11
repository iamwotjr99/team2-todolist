package com.team2.TodoList.common.domain.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "Invalid input value"),
    NOT_FOUND(404, "Not found data"),
    INTERNAL_ERROR(500, "unexpected error");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
