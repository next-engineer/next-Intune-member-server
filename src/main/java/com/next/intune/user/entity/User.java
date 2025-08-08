package com.next.intune.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "`users`")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`user_id`", nullable = false)
    @EqualsAndHashCode.Include
    private Long userId;

    @Column(name = "`email`", nullable = false, unique = true)
    private String email;

    @Column(name = "`password`", nullable = false)
    private String password;

    @Column(name = "`name`", nullable = false)
    private String name;

    @Column(name = "`mbti`", nullable = false)
    private String mbti;

    // 0 = USER, 1 = ADMIN
    @Builder.Default
    @Column(name = "`authority`", nullable = false,
            columnDefinition = "VARCHAR(1) DEFAULT '0' CHECK (authority IN ('0','1'))")
    private String authority = "0";

    // FK를 직접 관리할 수 있게 필드 추가
    @Column(name = "`main_profile_image_id`")
    private Long mainProfileImageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`main_profile_image_id`", referencedColumnName = "profile_image_id", insertable = false, updatable = false)
    private ProfileImage mainProfileImage;

    @Column(name = "`address`", nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "`via`", columnDefinition = "TEXT")
    private String via;

    @Builder.Default
    @Column(name = "`is_valid`", nullable = false,
            columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isValid = true;

    @Column(name = "`created_at`", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "`updated_at`", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}