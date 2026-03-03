package com.example.demo.repository;

import com.example.demo.model.UserEntity;
import com.example.demo.model.auth.LoginRequest;
import com.example.demo.model.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authaspect.service.JwtTokenIssuerService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenIssuerService jwtTokenIssuer;

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtTokenIssuer.issue(user.getEmail(), user.getRoles());
        var loginResponse = new LoginResponse(); // can be improved
        loginResponse.setEmail(user.getEmail());
        loginResponse.setRoles(user.getRoles());
        loginResponse.setToken(token);
        return loginResponse;
    }
}