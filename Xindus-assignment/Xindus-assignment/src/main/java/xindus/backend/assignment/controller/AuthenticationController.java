package xindus.backend.assignment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xindus.backend.assignment.entity.Users;
import xindus.backend.assignment.exception.GenericException;
import xindus.backend.assignment.model.AuthResponse;
import xindus.backend.assignment.repository.UserRepository;
import xindus.backend.assignment.util.JwtUtil;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Optional<Object>> requestBody) throws GenericException {
        if(!requestBody.containsKey("username") || !requestBody.containsKey("password"))
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Bad requests");
        String username= (String) requestBody.get("username")
                .orElseThrow(()->new GenericException(HttpStatus.BAD_REQUEST.value(), "Bad requests"));
        String password= (String) requestBody.get("password")
                .orElseThrow(()->new GenericException(HttpStatus.BAD_REQUEST.value(), "Bad requests"));
        Users user= userRepository.findByEmail(username)
                .orElseThrow(()->new GenericException(HttpStatus.BAD_REQUEST.value(), "Bad Credentials"));
        if(!bCryptPasswordEncoder.matches(password,user.getPassword()))
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Bad Credentials");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(jwtUtil.generateAccessToken(user), jwtUtil.generateRefreshToken(user)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> generateRefreshToken(@RequestBody Map<String,Optional<String>> refresh) throws GenericException {
        if(!refresh.containsKey("refreshToken"))
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Bad requests");
        String refreshToken=refresh.get("refreshToken")
                .orElseThrow(()->new GenericException(HttpStatus.BAD_REQUEST.value(), "Refresh token required"));
        if(!jwtUtil.isValidRefreshToken(refreshToken))
            throw new GenericException(HttpStatus.BAD_REQUEST.value(), "Invalid refresh token");
        Users user= userRepository.findByEmail(jwtUtil.extractUsername(refreshToken))
                .orElseThrow(()->new GenericException(HttpStatus.BAD_REQUEST.value(), "User does not exists"));
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(jwtUtil.generateAccessToken(user), jwtUtil.generateRefreshToken(user)));
    }
}