package com.next.intune.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CheckNameRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname; // 닉네임

}
