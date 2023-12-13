package com.example.PBL6.dto.user;

import com.example.PBL6.persistance.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserOrderDto {
    private Integer id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private Double amountPayed;

    public UserOrderDto(Integer id, String name, String email, String address, String phone, Double totalOrderPrice) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.amountPayed = totalOrderPrice != null ? totalOrderPrice : 0.0;
    }
}
