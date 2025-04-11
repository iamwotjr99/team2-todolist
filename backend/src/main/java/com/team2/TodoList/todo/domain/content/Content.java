package com.team2.TodoList.todo.domain.content;

import com.team2.TodoList.todo.domain.common.DateTimeInfo;

public abstract class Content {

    protected String contentText;
    protected DateTimeInfo dateTimeInfo;

    protected Content(String contentText) {
        checkText(contentText);
        this.contentText = contentText;
        this.dateTimeInfo = new DateTimeInfo();
    }

    public void updateContent(String contentText) {
        checkText(contentText);
        this.contentText = contentText;
        this.dateTimeInfo.updateEditDateTime();
    }

    protected abstract void checkText(String contentText);

    public String getContentText() {
        return contentText;
    }
}
