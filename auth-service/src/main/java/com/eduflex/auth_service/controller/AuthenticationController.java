package com.eduflex.auth_service.controller;

import com.eduflex.auth_service.dto.*;
import com.eduflex.auth_service.security.JwtService;
import com.eduflex.auth_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
    public class AuthenticationController {


        @Autowired
        JwtService jwtUtil;
        private final AuthenticationService authService;

        @PostMapping("/register")
        public ResponseEntity<MessageResponse> register(@RequestHeader("Authorization") String token, @RequestBody UserResponse request) throws BadRequestException {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String email = jwtUtil.extractEmail(token);
            if(authService.IsAdmin(email))
            {
                authService.register(request);
            }
            else {
                return ResponseEntity.ok(new MessageResponse("You do not have authority, just admin can add users to system"));
            }
            return ResponseEntity.ok(new MessageResponse("The User Register Successfully"));
        }

        @PostMapping("/login")
        public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest request) throws BadRequestException {
            AuthResponse response = authService.authenticate(request);
            return ResponseEntity.ok(response);
        }

        @PostMapping("/logout")
        public ResponseEntity<MessageResponse> logout(@RequestHeader("Authorization") String token) throws BadRequestException {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            String email = jwtUtil.extractEmail(token);
            authService.logout(email);
            return ResponseEntity.ok(new MessageResponse("Logout successfully"));
        }


    }
