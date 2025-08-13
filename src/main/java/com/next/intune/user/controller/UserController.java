package com.next.intune.user.controller;

import com.next.intune.common.api.ApiResult;
import com.next.intune.user.dto.request.CheckEmailRequestDto;
import com.next.intune.user.dto.request.CheckEmailResponseDto;
import com.next.intune.user.dto.request.SignInRequestDto;
import com.next.intune.user.dto.request.SignUpRequestDto;
import com.next.intune.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "회원가입",
            description = "신규 회원 정보를 등록합니다. 필수 입력값: 이메일, 비밀번호, 이름 등. "
                    + "입력한 이메일이 이미 존재할 경우 회원가입이 거부됩니다."
    )
    public ResponseEntity<ApiResult<?>> signUp(HttpServletResponse response, @Valid @RequestBody SignUpRequestDto dto) {
        userService.signUp(response, dto);
        return ResponseEntity.ok(ApiResult.success());
    }

    /* -----------------------
       이메일 중복 체크
       ----------------------- */
    @PostMapping("/check-email")
    @Operation(
            summary = "이메일 중복 체크",
            description = "입력한 이메일이 이미 등록되어 있는지 확인합니다. "
                    + "필수 입력값: 이메일 주소. "
                    + "응답: { available: true } → 사용 가능, { available: false } → 이미 존재."
    )
    @ApiResponse(responseCode = "200", description = "확인 성공")
    public ResponseEntity<ApiResult<CheckEmailResponseDto>> checkEmail(
            @Valid @RequestBody CheckEmailRequestDto dto
    ) {
        CheckEmailResponseDto result = userService.checkEmail(dto);
        return ResponseEntity.ok(ApiResult.success(result));
    }

    /* -----------------------
       회원 탈퇴
       ----------------------- */
    @PostMapping("/remove")
    @Operation(
            summary = "회원 탈퇴",
            description = "현재 로그인된 사용자를 탈퇴 처리합니다. "
                    + "요청 시 인증 토큰이 필요하며, 탈퇴 후 계정은 비활성화됩니다. "
                    + "관리자 요청 시 복구가 가능할 수 있습니다."
    )
    public ResponseEntity<ApiResult<?>> removeMember(HttpServletRequest request) {
        userService.removeMember(request);
        return ResponseEntity.ok(ApiResult.success());
    }
}
