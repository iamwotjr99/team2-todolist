package com.team2.TodoList.todo.application;

import com.team2.TodoList.todo.application.dto.CreateTodoRequestDto;
import com.team2.TodoList.todo.application.dto.UpdateTodoRequestDto;
import com.team2.TodoList.todo.application.interfaces.TodoRepository;
import com.team2.TodoList.todo.domain.Todo;
import com.team2.TodoList.todo.domain.TodoCategory;
import com.team2.TodoList.todo.ui.dto.GetTodoResponseDto;
import com.team2.TodoList.user.application.UserService;
import com.team2.TodoList.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;


    public Todo getTodo(Long id) {
        return todoRepository.findById(id);
    }

    public List<GetTodoResponseDto> getAllMyTodo(Long userId) {
        List<Todo> allTodo = todoRepository.findAllByUserId(userId);
        return allTodo.stream()
                .map(todo -> new GetTodoResponseDto(
                        todo.getId(),
                        todo.getAuthor().getId(),
                        todo.getContent(),
                        todo.getCount(),
                        todo.getCategoryText(),
                        todo.isDone(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    public List<GetTodoResponseDto> getAllTodoSortedByLatestForOthers(Long userId, int limit) {
        return todoRepository.findAllSortedByLatest(userId, limit).stream()
                .map(todo -> new GetTodoResponseDto(
                        todo.getId(),
                        todo.getAuthor().getId(),
                        todo.getContent(),
                        todo.getCount(),
                        todo.getCategoryText(),
                        todo.isDone(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()
                )).collect(Collectors.toList());
    }

    public List<GetTodoResponseDto> getTodosByCategoryForOthers(Long userId, String category, int limit) {
        return todoRepository.findAllByCategory(userId, TodoCategory.from(category), limit).stream()
                .map(todo -> new GetTodoResponseDto(
                        todo.getId(),
                        todo.getAuthor().getId(),
                        todo.getContent(),
                        todo.getCount(),
                        todo.getCategoryText(),
                        todo.isDone(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()
                )).collect(Collectors.toList());
    }

    public List<GetTodoResponseDto> getMyTodoByCategory(Long userId, String category) {
        return todoRepository.findAllByCategoryAndAuthorId(userId, TodoCategory.from(category)).stream()
                .map(todo -> new GetTodoResponseDto(
                        todo.getId(),
                        todo.getAuthor().getId(),
                        todo.getContent(),
                        todo.getCount(),
                        todo.getCategoryText(),
                        todo.isDone(),
                        todo.getCreatedAt(),
                        todo.getUpdatedAt()
                )).collect(Collectors.toList());
    }

    public Todo createTodo(Long userId, CreateTodoRequestDto dto) {
        User user = userService.getUserById(userId);
        Todo todo = Todo.createTodo(null, user, dto.content(), dto.category());
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long userId, Long todoId, UpdateTodoRequestDto dto) {
        User user = userService.getUserById(userId);
        Todo todo = getTodo(todoId);
        todo.updateTodo(user, dto.content(), dto.category());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long userId, Long todoId) {
        Todo todo = getTodo(todoId);
        if (!todo.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        todoRepository.deleteById(todoId);
    }

    @Transactional
    public void toggleTodo(Long userId, Long todoId) {
        Todo todo = getTodo(todoId);
        if (!todo.getAuthor().getId().equals(userId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        todo.toggleComplete();

        todoRepository.save(todo);
    }

}
