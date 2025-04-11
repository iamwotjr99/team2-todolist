package com.team2.TodoList.todo.application;

import com.team2.TodoList.todo.application.interfaces.LikeRepository;
import com.team2.TodoList.todo.application.interfaces.TodoRepository;
import com.team2.TodoList.todo.domain.Todo;
import com.team2.TodoList.todo.repository.entity.LikeEntity;
import com.team2.TodoList.todo.repository.jpa.JpaLikeRepository;
import com.team2.TodoList.todo.repository.jpa.JpaTodoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final JpaLikeRepository jpaLikeRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public boolean toggleLike(Long userId, Long todoId) {
        Todo todo = todoRepository.findById(todoId);
        Optional<LikeEntity> existingLike = jpaLikeRepository.findByUserIdAndTodoId(userId, todoId);

        boolean liked;

        if (existingLike.isPresent()) {
            jpaLikeRepository.deleteByUserIdAndTodoId(userId, todoId);
            todo.unlike();
            liked = false;
        } else {
            jpaLikeRepository.save(new LikeEntity(userId, todoId));
            todo.like();
            liked = true;
        }

        todoRepository.save(todo);
        return liked;

    }

}
