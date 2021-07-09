package org.project.spring.telecom.rate;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RateResultSetExtractor implements ResultSetExtractor<Optional<Rate>> {
    @Override
    public Optional<Rate> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.next()) {
            Rate rate = new Rate();
            rate.setId(resultSet.getLong("id"));
            rate.setProductId(resultSet.getLong("product_id"));
            rate.setName(resultSet.getString("name_rate"));
            rate.setPrice(resultSet.getDouble("price"));
            rate.setUnusable(resultSet.getBoolean("unusable"));
            return Optional.of(rate);
        }
        return Optional.empty();
    }
}
