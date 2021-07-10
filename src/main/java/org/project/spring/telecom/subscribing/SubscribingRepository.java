package org.project.spring.telecom.subscribing;

import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.project.spring.telecom.subscriber.dto.SubscriberAddSubscribingDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscribingRepository {

    private static Logger logger = LogManager.getLogger(SubscribingRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final SubscribingRowMapper subscribingRowMapper;

    public SubscriberAddSubscribingDTO addSubscribing(SubscriberAddSubscribingDTO subscriberDTO, Long productId, Long rateId) {
        String addSubscribing = "INSERT INTO subscribing VALUES(?,?,?)";
        String withdrawn = "UPDATE subscriber SET balance=? WHERE id=" + subscriberDTO.getId();
        jdbcTemplate.update(addSubscribing,
                preparedStatement -> {
                    preparedStatement.setLong(1, subscriberDTO.getId());
                    preparedStatement.setLong(2, productId);
                    preparedStatement.setLong(3, rateId);
                });
        jdbcTemplate.update(withdrawn,
                preparedStatement -> preparedStatement.setDouble(1, subscriberDTO.getBalance()));
        return subscriberDTO;
    }

    public List<Subscribing> getSubscribingBySubscriberId(Long id) {
        String getSubscribing = "SELECT user.login, subscriber.id, subscriber.balance, subscriber.locked, " +
                "subscribing.product_id, name_product, " +
                "rate_id, name_rate, rate.price, rate.unusable FROM subscribing " +
                "INNER JOIN user ON user.id = subscribing.subscriber_id " +
                "INNER JOIN subscriber ON subscriber.id = subscribing.subscriber_id " +
                "INNER JOIN rate ON rate.id = subscribing.rate_id " +
                "INNER JOIN product on product.id=subscribing.product_id WHERE subscriber_id=" + id;
        return jdbcTemplate.query(getSubscribing, subscribingRowMapper);
    }
}
