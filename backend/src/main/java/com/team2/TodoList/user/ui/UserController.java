package com.team2.TodoList.user.ui;

import com.team2.TodoList.common.ui.Response;
import com.team2.TodoList.user.application.UserService;
import com.team2.TodoList.user.application.dto.LoginRequestDto;
import com.team2.TodoList.user.application.dto.SignupRequestDto;
import com.team2.TodoList.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "입력한 값으로 회원가입을 시도합니다.")
    @PostMapping("/signup")
    public Response<Long> signup(@RequestBody SignupRequestDto dto) {
        User user = userService.signUp(dto);
        return Response.ok(user.getId());
    }

    @Operation(summary = "로그인", description = "입력한 값으로 로그인을 시도합니다.")
    @PostMapping("/login")
    public Response<String> login(@RequestBody LoginRequestDto dto) {
        String accessToken = userService.login(dto);
        return Response.ok(accessToken);
    }
}
