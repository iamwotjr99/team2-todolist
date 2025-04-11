package com.team2.TodoList.todo.domain;

public enum TodoCategory {
    공부,
    운동,
    취미;

    public static TodoCategory from(String value) {
        try {
            return TodoCategory.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다: " + value);
        }
    }

}
