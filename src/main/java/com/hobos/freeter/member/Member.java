package com.hobos.freeter.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Member {


    @Id
    @GeneratedValue()

    private Long id;

    private String providerId;

    private Provider provider;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private LocalDateTime loggedInAt;

    public void login(LoginDTO dto) {
        this.loggedInAt = LocalDateTime.now();
    }

    // 생성자, getter, setter 등 생략
    public enum Provider {
        GOOGLE,
        KAKAO,
        NAVER,
        LOCAL;
    }

    public void signup(SignupDTO dto) {
        // TODO: 불변객체로?
        this.providerId = dto.getProviderId();
        this.provider = dto.getProvider();

    }
}
