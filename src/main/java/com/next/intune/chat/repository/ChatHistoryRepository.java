package com.next.intune.chat.repository;

import com.next.intune.chat.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory,Long> {
}
