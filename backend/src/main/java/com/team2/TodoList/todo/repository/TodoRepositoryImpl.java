package com.team2.TodoList.todo.repository;

import com.team2.TodoList.todo.application.interfaces.TodoRepository;
import com.team2.TodoList.todo.domain.Todo;
import com.team2.TodoList.todo.domain.TodoCategory;
import com.team2.TodoList.todo.repository.entity.TodoEntity;
import com.team2.TodoList.todo.repository.jpa.JpaTodoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {

    private final JpaTodoRepository jpaTodoRepository;

    @Override
    @Transactional
    public Todo save(Todo todo) {
        TodoEntity todoEntity = new TodoEntity(todo);
        TodoEntity saved = jpaTodoRepository.save(todoEntity);
        return saved.toTodo();
    }

    @Override
    public Todo findById(Long id) {
        TodoEntity todoEntity = jpaTodoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Todo입니다."));
        return todoEntity.toTodo();
    }

    @Override
    public Optional<Todo> findByIdAndUserId(Long todoId, Long userId) {
        return jpaTodoRepository.findByIdAndAuthorId(todoId, userId)
                .map(TodoEntity::toTodo);
    }

    @Override
    public List<Todo> findAllByUserId(Long userId) {
        return jpaTodoRepository.findAllByAuthorId(userId)
                .stream()
                .map(TodoEntity::toTodo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findAllSortedByLatest(Long userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return jpaTodoRepository.findAllSortedByLatest(userId, pageable).stream()
                .map(TodoEntity::toTodo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findAllByCategory(Long userId, TodoCategory category, int limit) {
        Pageable pageable  = PageRequest.of(0, limit);
        return jpaTodoRepository.findAllByCategory(userId, category, pageable).stream()
                .map(TodoEntity::toTodo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Todo> findAllByCategoryAndAuthorId(Long userId, TodoCategory category) {
        return jpaTodoRepository.findAllByCategoryAndAuthorId(userId, category).stream()
                .map(TodoEntity::toTodo)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaTodoRepository.deleteById(id);
    }
}
