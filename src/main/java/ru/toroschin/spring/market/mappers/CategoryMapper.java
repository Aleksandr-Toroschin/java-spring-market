package ru.toroschin.spring.market.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.models.Category;
import ru.toroschin.spring.market.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * ДЗ 6 по архитектурам и шаблонам проектирования.
 */

@Service
@AllArgsConstructor
public class CategoryMapper {
    private final Connection connection;
    private Map<Long, Category> identityMap = new HashMap<>();

    public Category findById(Long id) throws SQLException {
        if (identityMap.containsKey(id)) {
            return identityMap.get(id);
        }
        PreparedStatement statement = connection.prepareStatement(
                "select id, title, created_at, updated_at from categories " +
                        "where id = ?");
        statement.setLong(1, id);
        try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Category category = new Category();
                category.setId(result.getLong(1));
                category.setTitle(result.getString(2));
                category.setCreatedAt(result.getTimestamp(3).toLocalDateTime());
                category.setUpdatedAt(result.getTimestamp(4).toLocalDateTime());
                identityMap.put(id, category);
                return category;
            }
        }
        return null;
    }

    public Optional<Category> findByTitle(String title) throws SQLException {
        for (Category value : identityMap.values()) {
            if (value.getTitle().equals(title)) {
                return Optional.of(value);
            }
        }
        PreparedStatement statement = connection.prepareStatement(
                "select id, title, created_at, updated_at from categories " +
                        "where title = ?");
        statement.setString(1, title);
        try (ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                Category category = new Category();
                category.setId(result.getLong(1));
                category.setTitle(result.getString(2));
                category.setCreatedAt(result.getTimestamp(3).toLocalDateTime());
                category.setUpdatedAt(result.getTimestamp(4).toLocalDateTime());
                identityMap.put(category.getId(), category);
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
