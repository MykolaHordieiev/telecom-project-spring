package org.project.spring.telecom;

import org.project.spring.telecom.infra.db.LiquibaseStarter;
import org.project.spring.telecom.infra.web.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext("org.project.spring.telecom.infra.config");

        //database
        LiquibaseStarter liquibaseStarter = applicationContext.getBean(LiquibaseStarter.class);
        liquibaseStarter.updateDatabase();

        //web
        ServerStarter serverStarter = applicationContext.getBean(ServerStarter.class);
        serverStarter.startServer();
    }
}