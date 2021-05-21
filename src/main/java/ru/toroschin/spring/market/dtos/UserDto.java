package ru.toroschin.spring.market.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.toroschin.spring.market.models.User;

@Data
@AllArgsConstructor
public class UserDto {
    private String name;
    private String email;

    public UserDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
