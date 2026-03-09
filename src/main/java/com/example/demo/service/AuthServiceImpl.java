package com.example.demo.service;

import com.example.demo.model.RefreshTokenEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.model.auth.LoginRequest;
import com.example.demo.model.auth.LoginResponse;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authaspect.config.JwtProperties;
import org.example.authaspect.exception.MissingTokenException;
import org.example.authaspect.service.JwtTokenIssuerService;
import org.example.authaspect.service.JwtValidationService;
import org.example.authaspect.service.TokenBlacklistService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenIssuerService jwtTokenIssuer;
    private final JwtValidationService jwtValidationService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtProperties jwtProperties;

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String accessToken = jwtTokenIssuer.issueAccessToken(user.getEmail(), user.getRoles());
        String refreshToken = saveRefreshToken(user.getEmail());

        var loginResponse = new LoginResponse();
        loginResponse.setRoles(user.getRoles());
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setEmail(user.getEmail());
        return loginResponse;
    }

    public LoginResponse refresh(String refreshToken) {
        RefreshTokenEntity entity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (entity.isRevoked() || entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadCredentialsException("Refresh token expired or revoked");
        }

        UserEntity user = userRepository.findByEmail(entity.getEmail())
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        // rotate refresh token — invalidate old, issue new
        entity.setRevoked(true);
        refreshTokenRepository.save(entity);
        String newRefreshToken = saveRefreshToken(user.getEmail());
        String newAccessToken = jwtTokenIssuer.issueAccessToken(user.getEmail(), user.getRoles());

        var loginResponse = new LoginResponse();
        loginResponse.setRoles(user.getRoles());
        loginResponse.setAccessToken(newAccessToken);
        loginResponse.setRefreshToken(newRefreshToken);
        loginResponse.setEmail(user.getEmail());
        return loginResponse;
    }

    @Transactional
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new MissingTokenException("Authorization header missing");
        }

        String raw = authHeader.replace("Bearer ", "").trim();
        Claims claims = jwtValidationService.validateAndExtractClaims(raw);

        long remainingTtl = claims.getExpiration().getTime() - System.currentTimeMillis();
        if (remainingTtl > 0) {
            tokenBlacklistService.blacklist(claims.getId(), remainingTtl);
        }

        refreshTokenRepository.deleteAllByEmail(claims.getSubject());
    }

    private String saveRefreshToken(String email) {
        String token = jwtTokenIssuer.issueRefreshToken(email);
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setToken(token);
        entity.setEmail(email);
        entity.setExpiresAt(LocalDateTime.now().plusNanos(
                jwtProperties.getRefreshTokenExpiry() * 1_000_000));
        refreshTokenRepository.save(entity);
        return token;
    }
}