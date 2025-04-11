package com.team2.TodoList.todo.repository.entity;

import com.team2.TodoList.todo.domain.TodoCategory;
import jakarta.persistence.AttributeConverter;

public class TodoCategoryConverter implements AttributeConverter<TodoCategory, String> {

    @Override
    public String convertToDatabaseColumn(TodoCategory attribute) {
        return attribute.name();
    }

    @Override
    public TodoCategory convertToEntityAttribute(String dbData) {
        return TodoCategory.valueOf(dbData);
    }
}
