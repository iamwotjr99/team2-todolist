package com.team2.TodoList.todo.repository.jpa;

import com.team2.TodoList.todo.domain.TodoCategory;
import com.team2.TodoList.todo.repository.entity.TodoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaTodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findAllByAuthorId(Long userId);

    Optional<TodoEntity> findByIdAndAuthorId(Long todoId, Long userId);

    @Query("SELECT t FROM TodoEntity t WHERE t.author.id <> :userId ORDER BY t.regDt DESC")
    List<TodoEntity> findAllSortedByLatest(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT t FROM TodoEntity t WHERE t.category = :category AND t.author.id <> :userId ORDER BY t.regDt DESC")
    List<TodoEntity> findAllByCategory(@Param("userId") Long userId,
                                        @Param("category") TodoCategory category,
                                        Pageable pageable);

    @Query("SELECT t FROM TodoEntity t WHERE t.category = :category AND t.author.id = :authorId")
    List<TodoEntity> findAllByCategoryAndAuthorId(@Param("authorId") Long userId, @Param("category") TodoCategory category);

}
