package com.team2.TodoList.common.ui;

import com.team2.TodoList.common.domain.exception.ErrorCode;

public record Response<T>(Integer code, String message, T data) {
    public static <T> Response<T> ok(T data) {
        return new Response<>(200, "ok", data);
    }

    public static <T> Response<T> error(ErrorCode errorCode) {
        return new Response<>(errorCode.getCode(), errorCode.getMessage(), null);
    }
}
