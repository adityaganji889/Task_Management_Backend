package com.taskmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagement.dto.JwtAuthenticationResponse;
import com.taskmanagement.dto.LoginRequestDTO;
import com.taskmanagement.dto.LoginResponseDTO;
import com.taskmanagement.dto.RefreshTokenRequestDTO;
import com.taskmanagement.dto.RegisterRequestDTO;
import com.taskmanagement.dto.RegisterResponseDTO;
import com.taskmanagement.entities.User;
import com.taskmanagement.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

	@Autowired
	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest) {
		RegisterResponseDTO response = new RegisterResponseDTO();
		if (authService.isEmailPresent(registerRequest.getEmail())) {
			response.setSuccess(false);
			response.setMessage("User already exists.");
			return new ResponseEntity<RegisterResponseDTO>(response, HttpStatus.BAD_REQUEST);
		} else {
			User user = authService.register(registerRequest);
			if (user != null) {
				response.setSuccess(true);
				response.setMessage("User : " + user.getName() + " is registered successfully.");
				return ResponseEntity.ok(response);
			} else {
				response.setSuccess(false);
				response.setMessage("User not registered successfully.");
				return new ResponseEntity<RegisterResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
		JwtAuthenticationResponse jwtResponse = null;
		User user = null;
		LoginResponseDTO response = new LoginResponseDTO();
		try {
			jwtResponse = authService.login(loginRequest);
			user = jwtResponse.getUser();
		} catch (BadCredentialsException e) {
			response.setSuccess(false);
			response.setMessage("Invalid Credentials");
			response.setToken(null);
			response.setRefreshToken(null);
			return new ResponseEntity<LoginResponseDTO>(response, HttpStatus.NOT_FOUND);
		}
		if (user != null) {
			response.setSuccess(true);
			response.setMessage("User : " + user.getName() + " is logged in successfully.");
			response.setToken(jwtResponse.getToken());
			response.setRefreshToken(jwtResponse.getRefreshToken());
			return ResponseEntity.ok(response);
		} else {
			response.setSuccess(false);
			response.setMessage("User not logged in successfully.");
			response.setToken("");
			response.setRefreshToken("");
			return ResponseEntity.ok(response);
		}
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
		JwtAuthenticationResponse jwtResponse = authService.refreshToken(refreshTokenRequest);
		User user = jwtResponse.getUser();
		LoginResponseDTO response = new LoginResponseDTO();
		if (user != null) {
			response.setSuccess(true);
			response.setMessage("User : " + user.getName() + " for him/her new jwt token is generated.");
			response.setToken(jwtResponse.getToken());
			response.setRefreshToken(jwtResponse.getRefreshToken());
			return ResponseEntity.ok(response);
		} else {
			response.setSuccess(false);
			response.setMessage("Unable to generate refresh token, new jwt token.");
			response.setToken("");
			response.setRefreshToken("");
			return ResponseEntity.ok(response);
		}
	}
}
