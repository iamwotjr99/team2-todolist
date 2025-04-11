package com.team2.TodoList.todo.ui.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTodoResponseDto {
    private Long id;
    private Long userId;
    private String content;
    private Integer likeCount;
    private String category;
    private boolean isDone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
