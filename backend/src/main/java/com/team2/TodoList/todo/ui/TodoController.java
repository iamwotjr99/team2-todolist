package com.team2.TodoList.todo.ui;

import com.team2.TodoList.common.ui.Response;
import com.team2.TodoList.todo.application.TodoService;
import com.team2.TodoList.todo.application.dto.CreateTodoRequestDto;
import com.team2.TodoList.todo.application.dto.UpdateTodoRequestDto;
import com.team2.TodoList.todo.domain.Todo;
import com.team2.TodoList.todo.ui.dto.GetTodoResponseDto;
import com.team2.TodoList.user.security.CustomUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Todo", description = "할 일 관련 API")
@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "나의 할 일 생성", description = "현재 로그인한 사용자가 형식에 맞게 할 일을 생성합니다.")
    @PostMapping
    public Response<Long> createTodo(@RequestBody CreateTodoRequestDto dto,
            @AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        Todo todo = todoService.createTodo(userId, dto);
        return Response.ok(todo.getId());
    }
    @Operation(summary = "나의 모든 할 일 조회", description = "현재 로그인한 사용자가 자신의 전체 할 일을 조회합니다. (카테고리: 전체)")
    @GetMapping
    public Response<List<GetTodoResponseDto>> getAllMyTodo(@AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        List<GetTodoResponseDto> todos = todoService.getAllMyTodo(userId);
        return Response.ok(todos);
    }

    @Operation(summary = "다른 사용자의 최근 모든 할 일 조회", description = "현재 로그인한 사용자가 다른 사람들의 전체 할 일을 조회합니다. (카테고리: 전체)")
    @GetMapping("/recent/other")
    public Response<List<GetTodoResponseDto>> getAllTodoSortedByLatest(
            @Parameter(description = "조회할 최대 개수 (기본 값 10개)") @RequestParam(defaultValue = "10") int limit,
            @AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        List<GetTodoResponseDto> todos = todoService.getAllTodoSortedByLatestForOthers(userId, limit);
        return Response.ok(todos);
    }

    @Operation(summary = "다른 사용자의 카테고리 별 최근 할 일 조회", description = "현재 로그인한 사용자가 다른 사람들의 카테고리 별 할 일을 조회합니다. (카테고리: 운동 or 공부 or 취미)")
    @GetMapping("/category/other")
    public Response<List<GetTodoResponseDto>> getAllTodoByCategory(
            @Parameter(description = "조회할 카테고리 (운동 or 공부 or 취미)") @RequestParam String category,
            @Parameter(description = "조회할 최대 개수 (기본 값 10개)") @RequestParam(defaultValue = "10") int limit, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        List<GetTodoResponseDto> todos = todoService.getTodosByCategoryForOthers(userId, category, limit);
        return Response.ok(todos);
    }

    @Operation(summary = "다른 사용자의 최근 모든 할 일 조회", description = "현재 로그인한 사용자가 다른 사람들의 전체 할 일을 조회합니다. (카테고리: 전체)")
    @GetMapping("/category/me")
    public Response<List<GetTodoResponseDto>> getMyTodoByCategory(
            @Parameter(description = "조회할 카테고리 (운동 or 공부 or 취미)") @RequestParam String category,
            @AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        List<GetTodoResponseDto> todos = todoService.getMyTodoByCategory(userId,
                category);

        return Response.ok(todos);
    }

    @Operation(summary = "나의 할 일을 수정", description = "현재 로그인한 사용자가 나의 특정 할 일을 수정합니다.")
    @PutMapping("/{todoId}")
    public Response<Long> updateTodo(
            @PathVariable(name = "todoId") Long todoId,
            @RequestBody UpdateTodoRequestDto dto, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        Todo todo = todoService.updateTodo(userId, todoId, dto);

        return Response.ok(todo.getId());
    }

    @Operation(summary = "나의 할 일을 삭제", description = "현재 로그인한 사용자가 나의 특정 할 일을 삭제합니다.")
    @DeleteMapping("/{todoId}")
    public Response<Long> deleteTodo(@PathVariable(name = "todoId") Long todoId, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        todoService.deleteTodo(userId, todoId);
        return Response.ok(todoId);
    }

    @Operation(summary = "할 일 완료 상태 토글", description = "해당 할 일의 완료 여부(isDone)를 토글합니다. 완료 → 미완료 또는 미완료 → 완료로 변경됩니다.")
    @PatchMapping("/{todoId}/toggle")
    public Response<Void> toggleTodo(@PathVariable(name = "todoId") Long todoId, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Long userId = userDetail.getId();
        todoService.toggleTodo(userId, todoId);
        return Response.ok(null);
    }
}
