package com.example.PBL6.service;

import com.example.PBL6.dto.user.AuthResponse;
import com.example.PBL6.dto.user.UserLoginDto;
import com.example.PBL6.dto.user.UserRegisterDto;

public interface AuthService {
    AuthResponse register(UserRegisterDto userRegisterDto);
    AuthResponse login(UserLoginDto userLoginDto);

}
