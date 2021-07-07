package ru.toroschin.spring.market.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.toroschin.spring.market.models.Category;
import ru.toroschin.spring.market.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ДЗ 6 по архитектурам и шаблонам проектирования.
 */

@Service
@AllArgsConstructor
public class ProductMapper {
    private final Connection connection;
    private final CategoryMapper categoryMapper;
    private Map<Long, Product> identityMap = new HashMap<>();

    public Product findById(Long id) throws SQLException {
        if (identityMap.containsKey(id)) {
            return identityMap.get(id);
        }
        PreparedStatement statement = connection.prepareStatement(
                "select id, title, cost, category_id, created_at, updated_at from products " +
                        "where id = ?");
        statement.setLong(1, id);
        try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getLong(1));
                product.setTitle(result.getString(2));
                product.setCost(result.getBigDecimal(3));
                product.setCreatedAt(result.getTimestamp(5).toLocalDateTime());
                product.setUpdatedAt(result.getTimestamp(6).toLocalDateTime());
                Long cat_id = result.getLong(4);
                Category category = categoryMapper.findById(cat_id);
                product.setCategory(category);
                identityMap.put(id, product);
                return product;
            }
        }
        return null;
    }

    public List<Product> findAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "select id, title, cost, category_id, created_at, updated_at from products");
        List<Product> products = new ArrayList<>();
        try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getLong(1));
                product.setTitle(result.getString(2));
                product.setCost(result.getBigDecimal(3));
                product.setCreatedAt(result.getTimestamp(5).toLocalDateTime());
                product.setUpdatedAt(result.getTimestamp(6).toLocalDateTime());
                Long cat_id = result.getLong(4);
                Category category = categoryMapper.findById(cat_id);
                product.setCategory(category);
                products.add(product);
            }
        }
        return products;
    }

    public void save(Product product) throws SQLException {
        if (product.getId() == null) {
            saveInsert(product);
        } else {
            saveUpdate(product);
            if (identityMap.containsKey(product.getId())) {
                identityMap.replace(product.getId(), product);
            }
        }
    }

    private void saveInsert(Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "insert into products (title, cost, category_id, created_at, updated_at)" +
                        "values (?, ?, ?, ?, ?)");
        statement.setString(1, product.getTitle());
        statement.setBigDecimal(2, product.getCost());
        statement.setLong(3, product.getCategory().getId());
        statement.setDate(4, Date.valueOf(LocalDate.now()));
        statement.setDate(5, Date.valueOf(LocalDate.now()));
        statement.execute();
        connection.commit();
    }

    private void saveUpdate(Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "Update products set title = ?, cost = ?, category_id = ?, updated_at = ? where id = ?");
        statement.setString(1, product.getTitle());
        statement.setBigDecimal(2, product.getCost());
        statement.setLong(3, product.getCategory().getId());
        statement.setDate(4, Date.valueOf(LocalDate.now()));
        statement.setLong(5, product.getId());
        statement.execute();
        connection.commit();
    }

    public void delete(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "Delete from products where id = ?");
        statement.setLong(1, id);
        statement.execute();
        connection.commit();
        if (identityMap.containsKey(id)) {
            identityMap.remove(id);
        }
    }
}
