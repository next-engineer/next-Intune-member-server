package com.next.intune.chat.repository;

import com.next.intune.chat.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match,Long> {
}
