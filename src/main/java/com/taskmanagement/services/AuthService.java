package com.taskmanagement.services;


import com.taskmanagement.dto.JwtAuthenticationResponse;
import com.taskmanagement.dto.LoginRequestDTO;
import com.taskmanagement.dto.RefreshTokenRequestDTO;
import com.taskmanagement.dto.RegisterRequestDTO;
import com.taskmanagement.entities.User;


public interface AuthService {

	User register(RegisterRequestDTO registerRequest);

	JwtAuthenticationResponse login(LoginRequestDTO loginRequest);

	JwtAuthenticationResponse refreshToken(RefreshTokenRequestDTO refreshTokenRequest);

	boolean isEmailPresent(String emailToFind);
	
}
