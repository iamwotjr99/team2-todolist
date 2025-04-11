package com.team2.TodoList.user.application.interfaces;

import com.team2.TodoList.user.domain.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    User findById(Long id);

    Optional<User> findOptionalByEmail(String email);
}
