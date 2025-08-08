package com.next.intune.user.service;

import com.next.intune.common.security.jwt.JwtProvider;
import com.next.intune.user.repository.ProfileImageRepository;
import com.next.intune.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
}
