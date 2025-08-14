package com.next.intune.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckNameResponseDto {

    /**
     * 닉네임 중복 체크 응답 DTO
     * - available: 사용 가능 여부
     *   true  → 사용 가능
     *   false → 이미 존재
     */
    private final boolean available;
}
