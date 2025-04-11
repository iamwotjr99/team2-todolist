package com.team2.TodoList.user.repository.jpa;

import com.team2.TodoList.user.repository.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserInfoEmail(String email);
}
