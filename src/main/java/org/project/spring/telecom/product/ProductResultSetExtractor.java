package org.project.spring.telecom.product;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class ProductResultSetExtractor implements ResultSetExtractor<Optional<Product>> {

    @Override
    public Optional<Product> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name_product"));
            return Optional.of(product);
        }
        return Optional.empty();
    }
}
