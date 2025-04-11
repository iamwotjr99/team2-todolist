package com.team2.TodoList.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 요청 DTO")
public record LoginRequestDto(
        @Schema(description = "로그인 할 이메일") String email,
        @Schema(description = "로그인 할 패스워드") String password) {

}
