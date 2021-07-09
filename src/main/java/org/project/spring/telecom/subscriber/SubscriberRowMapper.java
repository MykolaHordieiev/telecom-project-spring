package org.project.spring.telecom.subscriber;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriberRowMapper implements RowMapper<Subscriber> {

    @Override
    public Subscriber mapRow(ResultSet resultSet, int i) throws SQLException {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(resultSet.getLong("id"));
        subscriber.setLogin(resultSet.getString("login"));
        subscriber.setBalance(resultSet.getDouble("balance"));
        subscriber.setLock(resultSet.getBoolean("locked"));
        return subscriber;
    }
}
