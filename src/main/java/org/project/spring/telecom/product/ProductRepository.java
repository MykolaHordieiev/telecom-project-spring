package org.project.spring.telecom.product;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Product> getAllProducts() {
        String getAllProducts = "SELECT * FROM product ORDER BY id";
        return jdbcTemplate.query(getAllProducts, new ProductRowMapper());
    }

    public Optional<Product> getProductById(Long id) {
        String query = "SELECT * FROM product WHERE id = " + id;
        return jdbcTemplate.query(query, new ResultSetExtractorProduct());
    }
}
