package com.example.PBL6.controller;

import com.example.PBL6.dto.user.*;
import com.example.PBL6.persistance.user.User;
import com.example.PBL6.service.AuthService;
import com.example.PBL6.service.UserService;
import com.example.PBL6.util.AuthenticationUtils;
import com.example.PBL6.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("/all")
    public List<User> findAll() {
        List<User> rs = userService.findAll();
        return rs;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserRegisterDto userRegisterDto) {
        AuthResponse authResponse = authService.register(userRegisterDto);
        if (authResponse.getToken() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Gmail is ready exist");
        } else {
            return ResponseEntity.ok(authResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody UserLoginDto userLoginDto) {
        AuthResponse authResponse =  authService.login(userLoginDto);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        String email = JwtUtils.getUserEmailFromJwt(token);
        Optional<User> user =  userService.getUserProfile(email);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find user");
        }
    }

    @PostMapping("/profile/edit")
    public ResponseEntity<Object> editProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                            @RequestBody UserEditProfileDto userEditProfileDto) {
        String email = JwtUtils.getUserEmailFromJwt(token);
        User user =  userService.getUserProfile(email).orElse(null);
        System.out.println(userEditProfileDto);
        if(user != null) {
            try {
                User userUpdate = userService.editProfile(user.getId(), userEditProfileDto);
                return ResponseEntity.ok(userUpdate);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Edit fail");
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error");
        }
    }

    @GetMapping("/customerOrders")
    public ResponseEntity<Object> getCustomerOrders() {
        User user = AuthenticationUtils.getUserFromSecurityContext();
        if (user != null) {
            List<UserOrderDto> userOrderDtos = userService.getAllUserOrder();
            if(userOrderDtos == null || userOrderDtos.isEmpty()) {
                return ResponseEntity.ok("Chưa có khách hàng");
            } else {
                return ResponseEntity.ok(userOrderDtos);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
