package com.eduflex.auth_service.service;

import com.eduflex.auth_service.Enum.UserType;
import com.eduflex.auth_service.dto.*;
import com.eduflex.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
@EnableRetry
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final RestTemplate restTemplate;

    public Boolean IsAdmin(String email ) throws BadRequestException {
        ResponseEntity<UserResponse> response;

        try {
            response = restTemplate.getForEntity(
                    "http://user-service/api/users/"
                            + email,
                    UserResponse.class
            );
        } catch (Exception e) {
        throw new BadRequestException("User registration failed: " + e.getMessage());
        }
        if (response.getBody().getType().equals(UserType.ADMIN))
        return true;
        else return false;
    }

    @Value("${user.service.url}") // مثلا: http://localhost:8081
    private String userServiceUrl;

    @Retryable(
            value = { ResourceAccessException.class, IllegalStateException.class }, // أضف الأخطاء اللي ممكن تصير
            maxAttempts = 14,
            backoff = @Backoff(delay = 1000)
    )
    public AuthResponse register(UserResponse request) throws BadRequestException {
        logger.info("🔁 محاولة إرسال الطلب إلى user-service");  // داخل الميثود وبداية التنفيذ فعلاً

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName(request.getName());
        createUserRequest.setEmail(request.getEmail());
        createUserRequest.setPhoneNumber(request.getPhoneNumber());
        createUserRequest.setPassword(passwordEncoder.encode(request.getPassword()));
        createUserRequest.setType(request.getType());

        try {
            restTemplate.postForEntity(
                    "http://user-service/api/users",
                    createUserRequest,
                    Void.class
            );
            logger.info("✅ تم إرسال الطلب بنجاح إلى user-service");
        } catch (Exception e) {
            logger.error("❌ فشل الاتصال بـ user-service: {}", e.getMessage());
            throw e;
        }

        String accessToken = jwtService.generateAccessToken(request);
        String refreshToken = jwtService.generateRefreshToken(request);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .type(request.getType())
                .build();
    }

    @Recover
    public AuthResponse recover(ResourceAccessException e, UserResponse request) throws BadRequestException {
        logger.error("🚨 تم فشل كل المحاولات للتواصل مع user-service: {}", e.getMessage());
        throw new BadRequestException("User registration failed after retries: " + e.getMessage());
    }


//    @Retryable(
//            value = { ResourceAccessException.class }, // لما ما يقدر يتصل
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 1000) // 1 ثانية بين كل محاولة
//    )
//    public AuthResponse register(UserResponse request) throws BadRequestException {
//        CreateUserRequest createUserRequest = new CreateUserRequest();
//        createUserRequest.setName(request.getName());
//        createUserRequest.setEmail(request.getEmail());
//        createUserRequest.setPhoneNumber(request.getPhoneNumber());
//        createUserRequest.setPassword(passwordEncoder.encode(request.getPassword()));
//        createUserRequest.setType(request.getType());
//
//        try {
////            restTemplate.postForEntity(
////                    userServiceUrl + "/api/users",
////                    createUserRequest,
////                    Void.class
////            );
//            restTemplate.postForEntity(
//                    "http://user-service/api/users",
//                    createUserRequest,
//                    Void.class
//            );
//        } catch (Exception e) {
//            throw new BadRequestException("User registration failed: " + e.getMessage());
//        }
//
//        String accessToken = jwtService.generateAccessToken(request);
//        String refreshToken = jwtService.generateRefreshToken(request);
//
//        return AuthResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .type(request.getType())
//                .build();
//    }

    public AuthResponse authenticate(LoginRequest request) throws BadRequestException {
        ResponseEntity<UserResponse> response;

        try {
//            response = restTemplate.getForEntity(
//                    userServiceUrl + "/api/users/" + request.getEmail(),
//                    UserResponse.class
//            );
            response = restTemplate.getForEntity(
                    "http://user-service/api/users/"
                    + request.getEmail(),
                    UserResponse.class
            );
        } catch (Exception e) {
            throw new BadRequestException("User not found");
        }

        UserResponse user = response.getBody();
        if (user == null || !new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .type(user.getType())
                .build();
    }

    public void logout(String email) {
        System.out.println("Logging out: " + email);
    }
}
