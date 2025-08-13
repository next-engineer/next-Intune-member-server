package com.next.intune.user.controller;

import com.next.intune.common.api.ApiResult;
import com.next.intune.user.dto.request.SignInRequestDto;
import com.next.intune.user.dto.request.SignUpRequestDto;
import com.next.intune.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    /* -----------------------
       로그인
       ----------------------- */
    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "이메일과 비밀번호을 입력해주세요.")
    public ResponseEntity<ApiResult<?>> signIn(HttpServletResponse response, @Valid @RequestBody SignInRequestDto dto) {
        userService.signIn(response, dto);
        return ResponseEntity.ok(ApiResult.success());
    }

    /* -----------------------
       회원가입
       ----------------------- */
    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "항목을 입력해주세요.")
    public ResponseEntity<ApiResult<?>> signUp(HttpServletResponse response, @Valid @RequestBody SignUpRequestDto dto) {
        userService.signUp(response, dto);
        return ResponseEntity.ok(ApiResult.success());
    }

    /* -----------------------
       회원 탈퇴
       ----------------------- */
    @PostMapping("/remove")
    @Operation(summary = "유저 탈퇴", security = @SecurityRequirement(name="BearerAuth"))
    public ResponseEntity<ApiResult<?>> removeMember(HttpServletRequest request) {
        userService.removeMember(request);
        return ResponseEntity.ok(ApiResult.success());
    }
}
