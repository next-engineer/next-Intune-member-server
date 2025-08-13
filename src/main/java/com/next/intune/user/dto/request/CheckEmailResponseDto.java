package com.next.intune.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckEmailResponseDto {

    /**
     * 이메일 중복 체크 응답 DTO
     * - available: 사용 가능 여부
     *   true  → DB에 동일 이메일(유효 사용자)이 없음
     *   false → 이미 가입된 이메일 존재
     */
    private final boolean available;
}
