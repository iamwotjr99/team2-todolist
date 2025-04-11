package com.team2.TodoList.todo.application.dto;

import com.team2.TodoList.todo.domain.TodoCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "할 일 수정 요청 DTO")
public record UpdateTodoRequestDto(
        @Schema(description = "할 일 ID") Long todoId,
        @Schema(description = "할 일 내용") String content,
        @Schema(description = "카테고리") TodoCategory category
) {}
