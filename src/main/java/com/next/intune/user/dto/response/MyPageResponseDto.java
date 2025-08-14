package com.next.intune.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponseDto {

    @Schema(description = "이메일")
    private final String email;

    @Schema(description = "닉네임")
    private final String name;

    @Schema(description = "MBTI")
    private final String mbti;

    @Schema(description = "성별")
    private final String gender;

    @Schema(description = "주소")
    private final String address;

    @Schema(description = "프로필 이미지 전체 URL (없으면 기본 이미지 URL)")
    private final String profileImageUrl;

    @Schema(description = "가입일")
    private final String createdAt;
}
