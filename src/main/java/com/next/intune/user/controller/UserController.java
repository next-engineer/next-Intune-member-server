package com.next.intune.user.controller;

import com.next.intune.common.api.ApiResult;
import com.next.intune.user.dto.request.SignInRequestDto;
import com.next.intune.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResult<?>> signIn(HttpServletResponse response, @Valid @RequestBody SignInRequestDto dto) {
        userService.signIn(response, dto);
        return ResponseEntity.ok(ApiResult.success());
    }
}
