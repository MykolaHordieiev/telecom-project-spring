package org.project.spring.telecom.user;

import lombok.RequiredArgsConstructor;
import org.project.spring.telecom.user.dto.UserLoginDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserResultSetExtractor userResultSetExtractor;

    public Optional<User> getUserByLogin(UserLoginDTO userLoginDTO) {
        String query = "SELECT * FROM user WHERE login = ?";
        return jdbcTemplate.query(query,
                preparedStatement -> preparedStatement.setString(1, userLoginDTO.getLogin()),
                userResultSetExtractor);
    }
}
