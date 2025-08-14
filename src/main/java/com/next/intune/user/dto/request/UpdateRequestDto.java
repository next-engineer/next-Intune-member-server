package com.next.intune.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
@Getter
public class UpdateRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]|:;\"'<>,.?/~`]).{10,25}$",
            message = "비밀번호는 10자 이상 25자 이하이며, 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotBlank(message = "MBTI를 선택해주세요.")
    private String mbti;

    @NotBlank(message = "성별을 선택해주세요.")
    private String gender;

    private String profileimageName;

    @NotBlank(message = "주소를 입력해주세요.")
    private String address;
}