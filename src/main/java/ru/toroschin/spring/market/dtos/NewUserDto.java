package ru.toroschin.spring.market.dtos;

import lombok.Data;

@Data
public class NewUserDto {
    private String userName;
    private String password;
    private String email;
}
