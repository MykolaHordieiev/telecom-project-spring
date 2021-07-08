package org.project.spring.telecom.user;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.project.spring.telecom.operator.Operator;
import org.project.spring.telecom.subscriber.Subscriber;
import org.project.spring.telecom.user.dto.UserLoginDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<User> getUserByLogin(UserLoginDTO userLoginDTO) {
        String query = "SELECT * FROM user WHERE login = ?";
        return jdbcTemplate.query(query, preparedStatement -> preparedStatement.setString(1, userLoginDTO.getLogin()),
                new UserResultSetExtractor());
    }

//    @SneakyThrows
//    public Optional<User> getUserByLogin(UserLoginDTO userLoginDTO) {
//        String query = "SELECT * FROM user WHERE login=?";
//        ResultSet resultSet;
//        User foundUser;
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, userLoginDTO.getLogin());
//            resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                Long id = resultSet.getLong("id");
//                String password = resultSet.getString("password");
//                String role = resultSet.getString("role");
//                if (role.equals("OPERATOR")) {
//                    foundUser = new Operator();
//                    foundUser.setUserRole(UserRole.OPERATOR);
//                } else {
//                    foundUser = new Subscriber();
//                    foundUser.setUserRole(UserRole.SUBSCRIBER);
//                }
//                foundUser.setId(id);
//                foundUser.setLogin(userLoginDTO.getLogin());
//                foundUser.setPassword(password);
//                return Optional.of(foundUser);
//            }
//        }
//        return Optional.empty();
//    }
}