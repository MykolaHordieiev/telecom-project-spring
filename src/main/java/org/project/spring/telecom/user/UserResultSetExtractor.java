package org.project.spring.telecom.user;

import org.project.spring.telecom.operator.Operator;
import org.project.spring.telecom.subscriber.Subscriber;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserResultSetExtractor implements ResultSetExtractor<Optional<User>> {
    @Override
    public Optional<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        User foundUser;
        if (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            if (role.equals("OPERATOR")) {
                foundUser = new Operator();
                foundUser.setUserRole(UserRole.OPERATOR);
            } else {
                foundUser = new Subscriber();
                foundUser.setUserRole(UserRole.SUBSCRIBER);
            }
            foundUser.setId(id);
            foundUser.setLogin(login);
            foundUser.setPassword(password);
            return Optional.of(foundUser);
        }
        return Optional.empty();
    }
}
