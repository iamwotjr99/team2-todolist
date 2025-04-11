package com.team2.TodoList.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private final UserInfo userInfo;
    private final Password password;

    public static User createUser(String name, String email, String encodedPassword) {
        UserInfo userInfo = new UserInfo(name, email);
        Password password = Password.fromEncodedValue(encodedPassword);

        return new User(userInfo, password);
    }

    public User(UserInfo userInfo, Password password) {
        this.userInfo = userInfo;
        this.password = password;
    }

    public boolean checkPassword(String inputPassword, PasswordEncoder encoder) {
        return this.password.matches(inputPassword, encoder);
    }
}
