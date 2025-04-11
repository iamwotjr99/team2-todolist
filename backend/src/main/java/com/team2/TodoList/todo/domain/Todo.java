package com.team2.TodoList.todo.domain;

import com.team2.TodoList.common.domain.PositiveIntegerCount;
import com.team2.TodoList.todo.domain.content.Content;
import com.team2.TodoList.todo.domain.content.TodoContent;
import com.team2.TodoList.user.domain.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Todo {

    private Long id;
    private User author;
    private Content content;
    private PositiveIntegerCount likeCount;
    private TodoCategory category;
    private boolean done;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Todo createTodo(Long id, User author, String content, TodoCategory category) {
        return new Todo(id, author, new TodoContent(content), category);
    }


    public Todo(Long id, User author, Content content, TodoCategory category) {
        if(author == null) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.author = author;
        this.content = content;
        this.likeCount = new PositiveIntegerCount();
        this.category = category;
        this.done = false;
    }

    public void updateTodo(User author, String updateTodoText, TodoCategory category) {
        if(!this.author.getId().equals(author.getId())) {
            throw new IllegalArgumentException();
        }

        this.category = category;
        this.content.updateContent(updateTodoText);
    }

    public void like() {
        this.likeCount.increase();
    }

    public void unlike() {
        this.likeCount.decrease();
    }

    public void toggleComplete() {
        this.done = !this.done;
    }



    public String getContent() {
        return content.getContentText();
    }

    public String getCategoryText() {
        return category.name();
    }

    public int getCount() {
        return likeCount.getCount();
    }

}
