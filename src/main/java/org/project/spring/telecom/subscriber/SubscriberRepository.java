package org.project.spring.telecom.subscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.spring.telecom.subscriber.dto.SubscriberCreateDTO;
import org.project.spring.telecom.subscriber.dto.SubscriberReplenishDTO;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
@Log4j2
@RequiredArgsConstructor
public class SubscriberRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Subscriber> getById(Long id) {
        String query = "SELECT * FROM user JOIN subscriber ON user.id=subscriber.id WHERE user.id=" + id;
        return jdbcTemplate.query(query, new SubscriberResultSetExtractor());
    }

    public Optional<Subscriber> getByLogin(String login) {
        String query = "SELECT user.id, login, balance, locked FROM user JOIN subscriber ON user.id=subscriber.id " +
                "WHERE user.login='" + login + "'";
        return jdbcTemplate.query(query, new SubscriberResultSetExtractor());
    }

    public SubscriberCreateDTO insertSubscriber(SubscriberCreateDTO subscriberCreateDTO) {
        String insertIntoUser = "INSERT INTO user (login,password,role) VALUES (?,?,?);";
        String insertIntoSubscriber = "INSERT INTO subscriber (id) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertIntoUser, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, subscriberCreateDTO.getLogin());
            ps.setString(2, subscriberCreateDTO.getPassword());
            ps.setString(3, subscriberCreateDTO.getUserRole().toString());
            return ps;
        }, keyHolder);

        subscriberCreateDTO.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        jdbcTemplate.update(insertIntoSubscriber,
                preparedStatement -> preparedStatement.setLong(1, subscriberCreateDTO.getId()));
        return subscriberCreateDTO;
    }

    public List<Subscriber> getAll(int index) {
        String query = "SELECT * FROM user JOIN subscriber ON user.id=subscriber.id LIMIT ?, 5";
        return jdbcTemplate.query(query,
                preparedStatement -> preparedStatement.setInt(1, index),
                new SubscriberRowMapper());
    }

    public Double getCountOfRows() {
        String query = "SELECT COUNT(*) FROM user JOIN subscriber ON user.id=subscriber.id";
        return jdbcTemplate.query(query, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            return 0.0;
        });
    }

    public int lockSubById(Long id) {
        String query = "UPDATE subscriber SET locked=true WHERE id=" + id;
        return jdbcTemplate.update(query);
    }

    public int unlockSubById(Long id) {
        String query = "UPDATE subscriber SET locked=false WHERE id=" + id;
        return jdbcTemplate.update(query);
    }

    public SubscriberReplenishDTO replenishBalanceById(SubscriberReplenishDTO replenishDTO) {
        String query = "UPDATE subscriber SET balance=? WHERE id=" + replenishDTO.getId();
        jdbcTemplate.update(query, preparedStatement -> preparedStatement.setDouble(1, replenishDTO.getBalance()));
        return replenishDTO;
    }
}