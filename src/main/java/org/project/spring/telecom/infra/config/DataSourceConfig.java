package org.project.spring.telecom.infra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@Log4j2
@PropertySource("classpath:db/db.properties")
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(@Value("${jdbcURL}") String url,
                                 @Value("${mariadb.username}") String username,
                                 @Value("${mariadb.password}") String password) {
        log.info("Start config DataSource");

        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(url);
        log.info("Set JdbcUrl --> " + url);

        hikariConfig.setUsername(username);
        log.info("Set userName --> " + username);

        hikariConfig.setPassword(password);
        log.info("Set userPassword --> " + password);

        return new HikariDataSource(hikariConfig);
    }

}

