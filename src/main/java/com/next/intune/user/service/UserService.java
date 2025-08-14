package com.next.intune.user.service;

import com.next.intune.common.api.CustomException;
import com.next.intune.common.api.ResponseCode;
import com.next.intune.common.helper.CookieHelper;
import com.next.intune.common.security.jwt.JwtProvider;
import com.next.intune.user.dto.request.*;
import com.next.intune.user.entity.ProfileImage;
import com.next.intune.user.entity.User;
import com.next.intune.user.repository.ProfileImageRepository;
import com.next.intune.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;

    /**
     * 토큰을 만드는 메서드
     * 사용자의 정보를 기반으로 Access Token(JWT)을 생성
     */
    private String generateToken(User user) {
        return jwtProvider.generateAccessToken(user);
    }

    /**
     * 토큰 만료 시각을 사람이 읽을 수 있는 형태로 변환하는 메서드
     * JWT 토큰의 만료 시각을 UTC 시간 문자열로 변환하여 반환
     */
    private String generateTokenExpiresAt(String token) {
        // JWT 토큰의 만료 시각(밀리초 단위, Epoch Time)을 추출 (ex. yyyy-MM-ddThh:mm:ssZ → 1754991000000 (밀리초))
        long expiresAt = jwtProvider.getExpirationFromAccessToken(token);
        // yyyy-MM-ddThh:mm:ssZ 형식으로 변환
        return Instant.ofEpochMilli(expiresAt)
                .atZone(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);
    }

    /**
     * 발급한 토큰과 만료 정보를 쿠키에 저장하는 메서드
     * 보안 속성(HTTP-only, Secure, SameSite)을 적용하여 쿠키에 토큰을 설정
     */
    private void setTokenInCookie(HttpServletResponse response, String token, String expiresAt) {
        CookieHelper.addCookie(response, "access_token", token);
        CookieHelper.addCookie(response, "access_token_expires_at", expiresAt);
    }

    /**
     * 회원 가입 시 기본 프로필 이미지를 만들어주는 메서드
     * 프로필 이미지 엔티티 연관관계 세팅 (User <-> ProfileImage)
     */
    private ProfileImage insertProfileImage(User user) {
        return ProfileImage.builder()
                .profileImageName("profile-" + user.getUserId() + ".png")
                .build();
    }

    /**
     * 사용자 로그인 처리
     * - 입력한 이메일/비밀번호 검증 후 JWT 발급 및 쿠키 저장
     */
    public void signIn(HttpServletResponse response, SignInRequestDto dto) {
        User user = userRepository.findByEmailAndValidTrue(dto.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        String token = generateToken(user);
        String expiresAt = generateTokenExpiresAt(token);
        setTokenInCookie(response, token, expiresAt);
    }

    /**
     * 신규 회원 가입 처리
     * - 사용자 정보 저장, 기본 프로필 이미지 생성, JWT 발급 및 쿠키 저장
     */
    @Transactional
    public void signUp(HttpServletResponse response, SignUpRequestDto dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .mbti(dto.getMbti())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .build();
        userRepository.save(user);

        ProfileImage profileImage = insertProfileImage(user);
        profileImageRepository.save(profileImage);

        String token = generateToken(user);
        String expiresAt = generateTokenExpiresAt(token);
        setTokenInCookie(response, token, expiresAt);
    }

    /**
     * 이메일 중복 체크 (정규화 없이 원본 그대로 비교)
     * - 요청으로 들어온 이메일 문자열을 그대로 사용하여 유효 회원(valid=true) 기준 존재 여부를 확인합니다.
     * - available = true  → 사용 가능(중복 아님)
     * - available = false → 중복(이미 존재)
     */
    @Transactional(readOnly = true)
    public CheckEmailResponseDto checkEmail(CheckEmailRequestDto dto) {
        boolean exists = userRepository.existsByEmailAndValidTrue(dto.getEmail());
        return new CheckEmailResponseDto(!exists);
    }

    /**
     * 닉네임 중복 체크 (정규화 없이 원본 그대로 비교)
     * - 요청으로 들어온 name 문자열을 그대로 사용하여 유효 회원(valid=true) 기준 존재 여부 확인
     * - available = true  → 사용 가능(중복 아님)
     * - available = false → 중복(이미 존재)
     */
    @Transactional(readOnly = true)
    public CheckNameResponseDto checkName(CheckNameRequestDto dto) {
        boolean exists = userRepository.existsByNameAndValidTrue(dto.getName());
        return new CheckNameResponseDto(!exists);
    }

    /**
     * 회원 탈퇴 처리
     * - JWT로 사용자 인증 후, 회원 상태 유효성 (valid)를 'False'로 변경
     */
    public void removeMember(HttpServletRequest request) {
        String email = jwtProvider.extractEmailFromRequest(request);
        User user = userRepository.findByEmailAndValidTrue(email)
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));
        user.removeUser();
        userRepository.save(user);
    }

    /**
     * 회원 정보 수정
     * - JWT로 사용자 인증 후 회원정보 수정, 프로필이미지 기능 구현 안되었음.
     */
    @Transactional
    public void update(HttpServletResponse response, UpdateRequestDto dto) {
        User user = userRepository.findByEmailAndValidTrue(dto.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));


        User updateUser = User.builder()
                .userId(user.getUserId())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .mbti(dto.getMbti())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .build();

        userRepository.save(updateUser);

        /*
        ProfileImage profileImage = insertProfileImage(user);
        profileImageRepository.save(profileImage);
        */

        String token = generateToken(user);
        String expiresAt = generateTokenExpiresAt(token);
        setTokenInCookie(response, token, expiresAt);

    }
}
