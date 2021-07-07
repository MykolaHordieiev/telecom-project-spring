package org.project.spring.telecom.infra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@ComponentScan("org.project.spring.telecom")
@RequiredArgsConstructor
public class DataSourceConfig {

    private static Logger logger = LogManager.getLogger(DataSourceConfig.class);

    @Autowired
    private final ConfigLoader configLoader;

    @Bean
    public DataSource dataSource() {
        logger.info("Start config DataSource");

        configLoader.loadConfig();
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(configLoader.getJdbcUrl());
        logger.info("Set JdbcUrl --> " + configLoader.getJdbcUrl());

        hikariConfig.setUsername(configLoader.getUserName());
        logger.info("Set userName --> " + configLoader.getUserName());

        hikariConfig.setPassword(configLoader.getUserPassword());
        logger.info("Set userPassword --> " + configLoader.getUserPassword());

        return new HikariDataSource(hikariConfig);
    }

}

