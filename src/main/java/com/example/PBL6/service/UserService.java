package com.example.PBL6.service;

import com.example.PBL6.dto.user.UserEditProfileDto;
import com.example.PBL6.dto.user.UserOrderDto;
import com.example.PBL6.persistance.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> getUserProfile(String email);

    User editProfile(Integer id, UserEditProfileDto userEditProfileDto);

    List<UserOrderDto> getAllUserOrder();

}
