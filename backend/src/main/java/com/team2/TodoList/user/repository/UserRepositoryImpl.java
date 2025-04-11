package com.team2.TodoList.user.repository;

import com.team2.TodoList.user.application.interfaces.UserRepository;
import com.team2.TodoList.user.domain.User;
import com.team2.TodoList.user.repository.entity.UserEntity;
import com.team2.TodoList.user.repository.jpa.JpaUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity = jpaUserRepository.save(userEntity);
        return userEntity.toUser();
    }

    @Override
    public User findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(UserEntity::toUser)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    @Override
    public Optional<User> findOptionalByEmail(String email) {
        return jpaUserRepository.findByUserInfoEmail(email)
                .map(UserEntity::toUser);
    }
}
