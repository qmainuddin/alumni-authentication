package org.miu.alumni.alumniauthentication.service;

import org.miu.alumni.alumniauthentication.entity.dto.request.LoginRequest;
import org.miu.alumni.alumniauthentication.entity.dto.request.SignupDto;
import org.miu.alumni.alumniauthentication.entity.dto.response.LoginResponse;
import org.miu.alumni.alumniauthentication.entity.dto.request.RefreshTokenRequest;
import org.miu.alumni.alumniauthentication.entity.dto.response.SignupResponse;
import org.miu.alumni.alumniauthentication.service.impl.AwesomeUserDetails;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    SignupResponse signup(SignupDto loginRequest);
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    AwesomeUserDetails getCurrentlyLoggedInUser();
}
