package com.example.trendmart.controllers;

import com.example.trendmart.requests.CreateUserRequest;
import com.example.trendmart.requests.LoginRequest;
import com.example.trendmart.responeses.CustomApiResponse;
import com.example.trendmart.responeses.JwtResponse;
import com.example.trendmart.security.jwt.JwtUtils;
import com.example.trendmart.security.user.ShopUserDetails;
import com.example.trendmart.services.user.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "APIs for authenticating users" )
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateTokenForUser(authentication);

            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            return ResponseEntity.ok(new CustomApiResponse("Login successful", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CustomApiResponse(e.getMessage(), null));
        }

    }

    @PostMapping("/register")
    public ResponseEntity<CustomApiResponse> registerUser(@Valid @RequestBody CreateUserRequest registerRequest) {
        try {
            var user = userService.createUser(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomApiResponse("User registered successfully", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CustomApiResponse(e.getMessage(), null));
        }
    }
}
