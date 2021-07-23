package org.project.spring.telecom.infra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Log4j2
@Configuration
@PropertySource("classpath:db/db.properties")
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(@Value("${jdbcURL}") String url,
                                 @Value("${mysql.userame}") String userName,
                                 @Value("${mysql.userpassword}") String userPassword) {

        log.info("Start config DataSource");
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(url);
        log.info("Set JdbcUrl --> " + url);

        hikariConfig.setUsername(userName);
        log.info("Set userName --> " + userName);

        hikariConfig.setPassword(userPassword);
        log.info("Set userPassword --> " + userPassword);

        return new HikariDataSource(hikariConfig);
    }

}

