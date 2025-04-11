package com.team2.TodoList.todo.ui;

import com.team2.TodoList.common.ui.Response;
import com.team2.TodoList.todo.application.LikeService;
import com.team2.TodoList.user.security.CustomUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Like", description = "좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "좋아요 상태 토글", description = "해당 할 일의 나의 좋아요 여부를 토글합니다. 좋아요 → 좋아요 취소 또는 좋아요 취소 → 좋아요로 변경됩니다.")
    @PatchMapping("/{todoId}/like")
    public Response<Boolean> toggleLike(@PathVariable(name = "todoId") Long todoId, @AuthenticationPrincipal
            CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        boolean liked = likeService.toggleLike(userId, todoId);
        return Response.ok(liked);
    }
}
