package org.project.spring.telecom.subscriber;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SubscriberResultSetExtractor implements ResultSetExtractor<Optional<Subscriber>> {

    @Override
    public Optional<Subscriber> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        if (resultSet.next()) {
            Subscriber subscriber = new Subscriber();
            subscriber.setId(resultSet.getLong("id"));
            subscriber.setLogin(resultSet.getString("login"));
            subscriber.setBalance(resultSet.getDouble("balance"));
            subscriber.setLock(resultSet.getBoolean("locked"));
            return Optional.of(subscriber);
        }
        return Optional.empty();
    }
}
