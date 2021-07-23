package org.project.spring.telecom.infra.db;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class LiquibaseStarter {

    private final DataSource dataSource;
    @Value("${changelog.file}")
    private String CHANGE_LOG_FILE;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(CHANGE_LOG_FILE);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}