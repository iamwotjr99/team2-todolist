package com.team2.TodoList.user.security;

import com.team2.TodoList.user.application.interfaces.UserRepository;
import com.team2.TodoList.user.domain.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService {

    private final UserRepository userRepository;

    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId);

        return new CustomUserDetail(user.getId(), user.getUserInfo().email());
    }
}
