package com.team2.TodoList.todo.application.interfaces;

public interface LikeRepository {
    boolean toggleLike(Long userId, Long todoId);
}
