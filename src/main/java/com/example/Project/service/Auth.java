package com.example.Project.service;

import com.example.Project.dto.LoginRequest;
import com.example.Project.dto.ResetRequest;
import com.example.Project.dto.SignupRequest;
import com.example.Project.entity.User;

public interface Auth {

    public User createUser(SignupRequest signupRequest);

    public User resetPassword(ResetRequest resetRequest);

    public String getUsername(String email);
}
