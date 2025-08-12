package com.next.intune.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequestDto {
    @NotBlank(message = "이메일과 비밀번호를 입력해주세요.")
    private String email;
    @NotBlank(message = "이메일과 비밀번호를 입력해주세요.")
    private String password;
}
