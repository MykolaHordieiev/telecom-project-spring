package org.project.spring.telecom.rate;

import lombok.RequiredArgsConstructor;

import org.project.spring.telecom.rate.dto.RateAddRequestDTO;
import org.project.spring.telecom.rate.dto.RateChangeRequestDTO;
import org.project.spring.telecom.subscriber.Subscriber;
import org.project.spring.telecom.subscriber.SubscriberRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RateRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Rate> getRatesByProduct(Long productId) {
        String query = "SELECT * FROM rate WHERE product_id=" + productId;
        return jdbcTemplate.query(query, new RateRowMapper());
    }

    public List<Rate> getRatesBySubscriberId(Long subscriberId) {
        String query = "SELECT * FROM rate JOIN subscribing ON subscribing.rate_id=rate.id WHERE subscriber_id =" + subscriberId;
        return jdbcTemplate.query(query, new RateRowMapper());
    }

    public Optional<Rate> getRateById(Long id) {
        String query = "SELECT * FROM rate WHERE id=" + id;
        return jdbcTemplate.query(query, new RateResultSetExtractor());
    }

    public RateChangeRequestDTO changeRateById(RateChangeRequestDTO rateDTO) {
        String query = "UPDATE rate SET name_rate=?, price=? WHERE id=?";
        jdbcTemplate.update(query, preparedStatement -> {
            preparedStatement.setString(1, rateDTO.getRateName());
            preparedStatement.setDouble(2, rateDTO.getPrice());
            preparedStatement.setLong(3, rateDTO.getRateId());
        });
        return rateDTO;
    }

    public RateAddRequestDTO addRateByProductId(RateAddRequestDTO rateAddRequestDTO) {
        String addRate = "INSERT INTO rate (name_rate, price, product_id) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(addRate, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rateAddRequestDTO.getRateName());
            ps.setDouble(2, rateAddRequestDTO.getPrice());
            ps.setLong(3, rateAddRequestDTO.getProductId());
            return ps;
        }, keyHolder);
        rateAddRequestDTO.setRateId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return rateAddRequestDTO;
    }

    public Long deleteRateById(Long id) {
        String deleteQuery = "DELETE FROM rate WHERE id=" + id;
        jdbcTemplate.update(deleteQuery);
        return id;
    }

    public List<Subscriber> checkUsingRateBySubscribers(Long id) {
        String query = "SELECT subscriber.id, user.login, subscriber.balance, subscriber.locked FROM subscribing " +
                "JOIN user ON subscribing.subscriber_id=user.id " +
                "JOIN subscriber ON subscribing.subscriber_id=subscriber.id " +
                "WHERE rate_id=" + id;
        return jdbcTemplate.query(query, new SubscriberRowMapper());
    }

    public Rate doUnusableRateByRateId(Rate rate) {
        String query = "UPDATE rate SET unusable=true WHERE id=" + rate.getId();
        jdbcTemplate.update(query);
        rate.setUnusable(true);
        return rate;
    }
}
