package com.example.Project.controller;

import com.example.Project.dto.LoginResponse;
import com.example.Project.dto.LoginRequest;
import com.example.Project.dto.ResetRequest;
import com.example.Project.dto.SignupRequest;
import com.example.Project.entity.User;
import com.example.Project.service.Auth;
import com.example.Project.service.jwt.CustomerServiceImpl;
import com.example.Project.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private  CustomerServiceImpl customerService;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private  Auth auth;
    @Autowired
    private  AuthenticationManager authenticationManager;



    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        User newUser = auth.createUser(signupRequest);
        if (newUser != null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User SignUp Successful");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User SignUp Successful");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    User usr;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password.");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer is not activated");
            return null;
        }
        final UserDetails userDetails = customerService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername()  );


        return new LoginResponse(jwt,auth.getUsername(userDetails.getUsername()));
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetRequest resetRequest){
        User response = auth.resetPassword(resetRequest);
        if(response!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Email!");
        }
    };
}
