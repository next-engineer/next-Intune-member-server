package com.next.intune.user.service;

import com.next.intune.common.api.CustomException;
import com.next.intune.common.api.ResponseCode;
import com.next.intune.common.helper.CookieHelper;
import com.next.intune.common.security.jwt.JwtProvider;
import com.next.intune.user.dto.request.SignInRequestDto;
import com.next.intune.user.entity.User;
import com.next.intune.user.repository.ProfileImageRepository;
import com.next.intune.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void signIn(HttpServletResponse response, SignInRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(ResponseCode.LOGIN_ERROR);
        }

        String token = jwtProvider.generateAccessToken(user);
        long expiresAt = jwtProvider.getExpirationFromAccessToken(token);
        String utcExpiresAt = Instant.ofEpochMilli(expiresAt)
                .atZone(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);

        CookieHelper.addCookie(response, "access_token", token);
        CookieHelper.addCookie(response, "access_token_expires_at", utcExpiresAt);
    }

    public void removeMember(HttpServletRequest request) {
        String email = jwtProvider.extractEmailFromRequest(request);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.LOGIN_ERROR));
        user.removeUser();
        userRepository.save(user);
    }
}
