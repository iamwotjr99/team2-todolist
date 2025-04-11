package com.team2.TodoList.user.domain;

public record UserInfo(String name, String email) {

    public UserInfo {
        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            throw new IllegalArgumentException();
        }

    }

}
