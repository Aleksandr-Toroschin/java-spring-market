package ru.toroschin.spring.market;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.toroschin.spring.market.models.User;
import ru.toroschin.spring.market.repositories.UserRepository;
import ru.toroschin.spring.market.services.UserService;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUser() {
        User userDB = new User();
        userDB.setEmail("my@email.com");
        userDB.setName("User1");

        Mockito.doReturn(Optional.of(userDB))
                .when(userRepository)
                .findUsersByName("User1");

        User user = userService.findUserByUsername("User1").get();
        Assertions.assertNotNull(user);
        Assertions.assertEquals("User1", user.getName());
        Mockito.verify(userRepository, Mockito.times(1)).findUsersByName(ArgumentMatchers.eq("User1"));
    }
}
