package com.team2.TodoList.todo.domain.content;

public class TodoContent extends Content{

    private final static int MIN_TODO_LENGTH = 3;
    private final static int MAX_TODO_LENGTH = 20;

    public TodoContent(String contentText) {
        super(contentText);
    }

    @Override
    protected void checkText(String contentText) {
        if(contentText == null || contentText.isEmpty() || contentText.length() < MIN_TODO_LENGTH ||
            contentText.length() > MAX_TODO_LENGTH) {
            throw new IllegalArgumentException();
        }
    }
}
