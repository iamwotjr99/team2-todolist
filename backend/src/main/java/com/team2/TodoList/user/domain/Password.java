package com.team2.TodoList.user.domain;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class Password {
    private final String value;

    private Password(String encodedValue) {
        this.value = encodedValue;
    }

    // UserEntity 객체에서 User 도메인 객체로 변환할 때 사용
    // UserEntity 객체에서는 pw가 String 타입이지만, User 도메인 객체에서는 pw가 Password 타입임
    public static Password fromEncodedValue(String encodedValue) {
        return new Password(encodedValue);
    }

    public static void validationRawPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("비밀번호는 최소 6자 이상이어야 합니다.");
        }
    }

    public boolean matches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.value);
    }

}
