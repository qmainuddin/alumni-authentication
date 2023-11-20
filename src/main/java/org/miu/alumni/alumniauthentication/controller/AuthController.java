package org.miu.alumni.alumniauthentication.controller;

import org.miu.alumni.alumniauthentication.entity.dto.request.LoginRequest;
import org.miu.alumni.alumniauthentication.entity.dto.request.SignupDto;
import org.miu.alumni.alumniauthentication.entity.dto.response.LoginResponse;
import org.miu.alumni.alumniauthentication.entity.dto.request.RefreshTokenRequest;
import org.miu.alumni.alumniauthentication.entity.dto.response.SignupResponse;
import org.miu.alumni.alumniauthentication.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> singin(@RequestBody LoginRequest loginRequest) {
        var loginResponse = authService.login(loginRequest);
        return new ResponseEntity<LoginResponse>(
                loginResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDto signupDto) {
        var signupResponse = authService.signup(signupDto);
        return new ResponseEntity<SignupResponse>(
                signupResponse, HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public LoginResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

}
