package com.next.intune.user.controller;

import com.next.intune.common.api.ApiResult;
import com.next.intune.user.dto.request.SignInRequestDto;
import com.next.intune.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "Hello Controller", description = "회원 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "이메일과 비밀번호을 입력해주세요.")
    public ResponseEntity<ApiResult<?>> signIn(HttpServletResponse response, @Valid @RequestBody SignInRequestDto dto) {
        userService.signIn(response, dto);
        return ResponseEntity.ok(ApiResult.success());
    }
}
