package com.team2.TodoList.todo.repository.jpa;

import com.team2.TodoList.todo.repository.entity.LikeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserIdAndTodoId(Long userId, Long todoId);
    void deleteByUserIdAndTodoId(Long userId, Long todoId);
}
