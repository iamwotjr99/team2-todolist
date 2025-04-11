package com.team2.TodoList.todo.repository;

import com.team2.TodoList.todo.application.interfaces.LikeRepository;
import com.team2.TodoList.todo.repository.entity.LikeEntity;
import com.team2.TodoList.todo.repository.jpa.JpaLikeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

    private final JpaLikeRepository jpaLikeRepository;

    @Override
    public boolean toggleLike(Long userId, Long todoId) {
        Optional<LikeEntity> like = jpaLikeRepository.findByUserIdAndTodoId(userId,
                todoId);
        if(like.isPresent()) {
            jpaLikeRepository.deleteByUserIdAndTodoId(userId, todoId);
            return false;
        }

        jpaLikeRepository.save(new LikeEntity(userId, todoId));
        return true;
    }
}
