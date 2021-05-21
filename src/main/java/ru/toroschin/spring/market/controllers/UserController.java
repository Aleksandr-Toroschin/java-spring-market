package ru.toroschin.spring.market.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.toroschin.spring.market.dtos.NewUserDto;
import ru.toroschin.spring.market.services.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> registrationNewUser(@RequestBody NewUserDto newUserDto) {
        return userService.registrationNewUser(newUserDto);
    }
}
