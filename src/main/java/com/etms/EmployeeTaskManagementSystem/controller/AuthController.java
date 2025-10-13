package com.etms.EmployeeTaskManagementSystem.controller;

import com.etms.EmployeeTaskManagementSystem.dto.RegisterRequest;
import com.etms.EmployeeTaskManagementSystem.dto.LoginRequest;
import com.etms.EmployeeTaskManagementSystem.dto.AuthResponse;
import com.etms.EmployeeTaskManagementSystem.model.User;
import com.etms.EmployeeTaskManagementSystem.model.Role;
import com.etms.EmployeeTaskManagementSystem.repository.UserRepository;
import com.etms.EmployeeTaskManagementSystem.security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYEE); // Always default to EMPLOYEE on new registration

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        AuthResponse response = new AuthResponse(token, user.getRole().name(), user.getId(), user.getName());
        return ResponseEntity.ok(response);
    }
//
//    // LOGIN
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
//        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name()));
//    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user);

        AuthResponse response = new AuthResponse(token, user.getRole().name(), user.getId(), user.getName());
        return ResponseEntity.ok(response);
    }

}
