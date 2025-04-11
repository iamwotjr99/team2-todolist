package com.team2.TodoList.todo.application.dto;

import com.team2.TodoList.todo.domain.TodoCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "할 일 생성 요청 DTO")
public record CreateTodoRequestDto(
        @Schema(description = "할 일 내용", example = "5km 런닝하기") String content,
        @Schema(description = "카테고리", example = "운동") TodoCategory category
) {}
