package com.example.PBL6.service.impl;

import com.example.PBL6.dto.user.UserEditProfileDto;
import com.example.PBL6.dto.user.UserOrderDto;
import com.example.PBL6.persistance.user.User;
import com.example.PBL6.repository.UserRepository;
import com.example.PBL6.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserProfile(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) {
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public User editProfile(Integer id, UserEditProfileDto userEditProfileDto) {
        userRepository.updateUserFields(id,
                userEditProfileDto.getName(),
                userEditProfileDto.getAddress(),
                userEditProfileDto.getPhone(),
                userEditProfileDto.getAvatar(),
                userEditProfileDto.getGender().name());
        System.out.println(userEditProfileDto);
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(null);
    }

    @Override
    public List<UserOrderDto> getAllUserOrder() {
        List<UserOrderDto> userOrderDtoList = new ArrayList<>();
        List<Object[]> result = userRepository.getAllUserOrder();

        for (Object[] row : result) {
            UserOrderDto userOrderDto = new UserOrderDto(
                    (Integer) row[0],     // id
                    (String) row[1],      // name
                    (String) row[2],      // email
                    (String) row[3],      // address
                    (String) row[4],      // phone
                    (Double) row[5]       // total_order_price
            );
            userOrderDtoList.add(userOrderDto);
        }

        return userOrderDtoList;
    }
}
