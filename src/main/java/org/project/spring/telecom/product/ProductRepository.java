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

    public List<Product> getAllProducts() {
        String getAllProducts = "SELECT * FROM product ORDER BY id";
        return jdbcTemplate.query(getAllProducts, new ProductRowMapper());
    }

    public Optional<Product> getProductById(Long id) {
        String query = "SELECT * FROM product WHERE id = " + id;
        return jdbcTemplate.query(query, new ResultSetExtractorProduct());
    }
}
