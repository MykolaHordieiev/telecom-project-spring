package org.project.spring.telecom.rate;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RateRowMapper implements RowMapper<Rate> {
    @Override
    public Rate mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name_rate");
        double prise = resultSet.getDouble("price");
        long prodId = resultSet.getLong("product_id");
        boolean unusable = resultSet.getBoolean("unusable");
        return new Rate(id, name, prise, prodId, unusable);
    }
}
