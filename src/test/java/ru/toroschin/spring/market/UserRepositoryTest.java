package ru.toroschin.spring.market;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.toroschin.spring.market.models.User;
import ru.toroschin.spring.market.repositories.ProductRepository;
import ru.toroschin.spring.market.repositories.UserRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void userTest() {
        User user = new User();
        user.setName("User4");
        user.setEmail("my@email.com");

        testEntityManager.persist(user);
        testEntityManager.flush();

        List<User> users = userRepository.findAll();

        System.out.println(users);
        Assertions.assertEquals(4, users.size());
    }
}
