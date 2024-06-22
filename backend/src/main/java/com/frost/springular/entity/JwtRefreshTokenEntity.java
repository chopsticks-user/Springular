package com.frost.springular.entity;

import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "jwt_refresh_tokens")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = true)
    private String token;

    @Column(nullable = true)
    private Instant expirationDate;

    @OneToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false, unique = true)
    private UserEntity userEntity;
}
