package org.project.spring.telecom.infra.db;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Log4j2
public class LiquibaseStarter {

    private final static String CHANGE_LOG_FILE = "/db/liquibase/db-changelog-master.xml";

    private final DataSource dataSource;
    private final AtomicBoolean alreadyStarted = new AtomicBoolean(false);

    @SneakyThrows
    @EventListener(ContextRefreshedEvent.class)
    public void updateDatabase() {
        if (!alreadyStarted.get()) {
            try (Connection connection = dataSource.getConnection()) {
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                Liquibase liquibase = new Liquibase(CHANGE_LOG_FILE, new ClassLoaderResourceAccessor(), database);
                liquibase.update(new Contexts(), new LabelExpression());
                alreadyStarted.set(true);
                log.info("Update database");
            }
        }
    }
}