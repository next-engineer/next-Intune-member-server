package com.next.intune.chat.service;

import com.next.intune.chat.repository.ChatHistoryRepository;
import com.next.intune.chat.repository.ChatImageRepository;
import com.next.intune.chat.repository.MatchRepository;
import com.next.intune.common.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final JwtProvider jwtProvider;
    private final MatchRepository matchRepository;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatImageRepository chatImageRepository;

}
