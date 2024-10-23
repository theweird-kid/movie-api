package com.movieflix.movie_api.controllers;

import com.movieflix.movie_api.auth.entities.RefreshToken;
import com.movieflix.movie_api.auth.entities.User;
import com.movieflix.movie_api.auth.services.AuthService;
import com.movieflix.movie_api.auth.services.JwtService;
import com.movieflix.movie_api.auth.services.RefreshTokenService;
import com.movieflix.movie_api.auth.utils.AuthResponse;
import com.movieflix.movie_api.auth.utils.LoginRequest;
import com.movieflix.movie_api.auth.utils.RefreshTokenRequest;
import com.movieflix.movie_api.auth.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService,
                          RefreshTokenService refreshTokenService,
                          JwtService jwtService)
    {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    // register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    //refresh token
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());

        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getRefreshToken())
                        .build()
        );
    }
}
