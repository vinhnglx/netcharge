package com.vinhnguyen.netChargeNZ.controller;

import com.vinhnguyen.netChargeNZ.constants.SecurityConstants;
import com.vinhnguyen.netChargeNZ.controller.request.LoginRequest;
import com.vinhnguyen.netChargeNZ.controller.response.RegisterResponse;
import com.vinhnguyen.netChargeNZ.model.User;
import com.vinhnguyen.netChargeNZ.service.UserService;
import com.vinhnguyen.netChargeNZ.util.JWTTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody User requestUser) {
        User savedUser;
        RegisterResponse registerResponse = new RegisterResponse();
        ResponseEntity response = null;

        try {
            savedUser = userService.createUser(requestUser);

            registerResponse.setId(savedUser.getId());
            registerResponse.setUsername(savedUser.getUsername());

            response = ResponseEntity.status(HttpStatus.CREATED)
                    .body(registerResponse);
        } catch (Exception e) {
            registerResponse.setError("An exception occurred due to " + e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(registerResponse);
        }
        return response;
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest userRequest) {
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();

        User user = userService.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        String token = jwtTokenUtil.generate(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.JWT_HEADER, "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority: collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }
}
