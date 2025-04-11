package com.team2.TodoList.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청 DTO")
public record SignupRequestDto(
        @Schema(description = "회원 가입 시 나의 이름") String name,
        @Schema(description = "회원 가입 시 나의 이메일") String email,
        @Schema(description = "회원 가입 시 나의 패스워드") String password) {

}
