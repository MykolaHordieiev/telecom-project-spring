package org.project.spring.telecom.infra.config;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Data
@Component
public class ConfigLoader {

    private static Logger logger = LogManager.getLogger(ConfigLoader.class);

    private String jdbcUrl;
    private String userName;
    private String userPassword;

    void loadConfig() {
        Properties property = new Properties();
        try {
            logger.info("start load config");
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db/db.properties");
            property.load(inputStream);

            this.jdbcUrl = property.getProperty("jdbcURL");
            logger.info("load jdbcURL --> " + jdbcUrl);

            this.userName = property.getProperty("userName");
            logger.info("load userName --> " + userName);

            this.userPassword = property.getProperty("userPassword");
            logger.info("load userPassword --> " + userPassword);
        } catch (Exception ex) {
            logger.error("Cannot load properties. ", ex);
        }
    }

}
