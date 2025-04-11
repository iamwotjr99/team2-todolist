package com.team2.TodoList.todo.application.interfaces;

import com.team2.TodoList.todo.domain.Todo;
import com.team2.TodoList.todo.domain.TodoCategory;
import com.team2.TodoList.todo.repository.entity.TodoEntity;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    Todo save(Todo todo);

    Todo findById(Long id);

    Optional<Todo> findByIdAndUserId(Long todoId, Long userId);

    List<Todo> findAllByUserId(Long userId);

    List<Todo> findAllSortedByLatest(Long userId, int limit);

    List<Todo> findAllByCategory(Long userId, TodoCategory category, int limit);

    List<Todo> findAllByCategoryAndAuthorId(Long authorId, TodoCategory category);

    void deleteById(Long id);
}
