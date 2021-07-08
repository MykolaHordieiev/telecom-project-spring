package org.project.spring.telecom.subscribing;

import org.project.spring.telecom.product.Product;
import org.project.spring.telecom.rate.Rate;
import org.project.spring.telecom.subscriber.Subscriber;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscribingRowMapper implements RowMapper<Subscribing> {

    @Override
    public Subscribing mapRow(ResultSet resultSet, int i) throws SQLException {
        Subscriber subscriber = new Subscriber();
        subscriber.setId(resultSet.getLong("id"));
        subscriber.setLogin(resultSet.getString("login"));
        subscriber.setBalance(resultSet.getDouble("balance"));
        subscriber.setLock(resultSet.getBoolean("locked"));

        Product product = new Product();
        product.setId(resultSet.getLong("product_id"));
        product.setName(resultSet.getString("name_product"));

        Rate rate = new Rate();
        rate.setId(resultSet.getLong("rate_id"));
        rate.setName(resultSet.getString("name_rate"));
        rate.setPrice(resultSet.getDouble("price"));
        rate.setProductId(resultSet.getLong("product_id"));
        rate.setUnusable(resultSet.getBoolean("unusable"));

        Subscribing subscribing = new Subscribing();
        subscribing.setSubscriber(subscriber);
        subscribing.setProduct(product);
        subscribing.setRate(rate);

        return subscribing;
    }
}
