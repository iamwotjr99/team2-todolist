package com.team2.TodoList.user.application;

import com.team2.TodoList.common.security.jwt.JwtProvider;
import com.team2.TodoList.user.application.dto.LoginRequestDto;
import com.team2.TodoList.user.application.dto.SignupRequestDto;
import com.team2.TodoList.user.application.interfaces.UserRepository;
import com.team2.TodoList.user.domain.Password;
import com.team2.TodoList.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public User signUp(SignupRequestDto dto) {
        if (userRepository.findOptionalByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }
        Password.validationRawPassword(dto.password());
        String encodedPassword = passwordEncoder.encode(dto.password());

        User user = User.createUser(dto.name(), dto.email(), encodedPassword);

        return userRepository.save(user);
    }

    public String login(LoginRequestDto dto) {
        User user = userRepository.findOptionalByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if (!user.getPassword().matches(dto.password(), passwordEncoder)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return jwtProvider.generateToken(user.getId());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
}
