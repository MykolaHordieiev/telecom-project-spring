package org.project.spring.telecom.product;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProductRowMapper productRowMapper;
    private final ProductResultSetExtractor productResultSetExtractor;

    public List<Product> getAllProducts() {
        String getAllProducts = "SELECT * FROM product ORDER BY id";
        return jdbcTemplate.query(getAllProducts, productRowMapper);
    }

    public Optional<Product> getProductById(Long id) {
        String query = "SELECT * FROM product WHERE id = " + id;
        return jdbcTemplate.query(query, productResultSetExtractor);
    }
}
