package ru.toroschin.spring.market;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.toroschin.spring.market.models.User;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserJsonTest {
    @Autowired
    private JacksonTester<User> userJson;

    @Test
    public void jsonSerializationTest() throws IOException {
        User user = new User();
        user.setId(1L);
        user.setName("User1");
        user.setEmail("my@email.com");

        assertThat(userJson.write(user))
                .hasJsonPath("$.id")
                .extractingJsonPathStringValue("$.name").isEqualTo("User1");
    }

    @Test
    public void jsonDeserializationTest() throws Exception {
        String content = "{\"id\": 2,\"name\":\"User1\", \"email\": \"my@email.com\"}";
        User user = new User();
        user.setId(2L);
        user.setName("User1");
        user.setEmail("my@email.com");

        assertThat(userJson.parse(content)).isEqualTo(user);
        assertThat(userJson.parseObject(content).getName()).isEqualTo("User1");
    }

}
