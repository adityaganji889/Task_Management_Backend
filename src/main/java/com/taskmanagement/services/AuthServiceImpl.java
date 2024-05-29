package com.taskmanagement.services;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanagement.dto.JwtAuthenticationResponse;
import com.taskmanagement.dto.LoginRequestDTO;
import com.taskmanagement.dto.RefreshTokenRequestDTO;
import com.taskmanagement.dto.RegisterRequestDTO;
import com.taskmanagement.entities.User;
import com.taskmanagement.enums.UserRole;
import com.taskmanagement.repositories.UserRepository;
import com.taskmanagement.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Autowired
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

	public User register(RegisterRequestDTO registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setName(registerRequest.getName());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setRole(UserRole.USER);
		userRepository.save(user);
		return user;
	}

	public boolean isEmailPresent(String emailToFind) {
		return userRepository.findFirstByEmail(emailToFind).isPresent();
	}

	public JwtAuthenticationResponse login(LoginRequestDTO loginRequest) {
		JwtAuthenticationResponse response = new JwtAuthenticationResponse();
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		var user = userRepository.findFirstByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
		if (user != null) {
			user.setPassword("");
		}
		var jwt = jwtUtil.generateToken(user);
		var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);
		response.setUser(user);
		response.setToken(jwt);
		response.setRefreshToken(refreshToken);
		return response;
	}

	public JwtAuthenticationResponse refreshToken(RefreshTokenRequestDTO refreshTokenRequest) {
		JwtAuthenticationResponse response = new JwtAuthenticationResponse();
		String userEmail = jwtUtil.extractUserName(refreshTokenRequest.getToken());
		User user = userRepository.findFirstByEmail(userEmail).orElseThrow();
		if (user != null && jwtUtil.isTokenValid(refreshTokenRequest.getToken(), user)) {
			user.setPassword("");
			var jwt = jwtUtil.generateToken(user);
			var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(), user);
			response.setUser(user);
			response.setToken(jwt);
			response.setRefreshToken(refreshTokenRequest.getToken());
		}
		return response;
	}

}
