package ru.toroschin.spring.market;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.toroschin.spring.market.dtos.ProductDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FullProductsTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
//    @WithMockUser(username = "User1", roles = "USER")
    public void allProductsTest() {
        List<ProductDto> products = testRestTemplate.getForObject("/api/v1/products/list", List.class);
        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();
    }

    @Test
    public void idProductsTest() {
        ProductDto product = testRestTemplate.getForObject("/api/v1/products/1", ProductDto.class);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(1L);
    }

//    Здесь почему-то возникает ошибка сериализации Page. Может ли быть, что Page для этого метода не предназначен?
//    @Test
//    public void fullProducts() {
//        Page<ProductDto> page = testRestTemplate.getForObject("/api/v1/products", Page.class);
//        assertThat(page).isNotNull();
//        assertThat(page).isNotEmpty();
//    }
}
