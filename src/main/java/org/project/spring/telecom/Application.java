package org.project.spring.telecom;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;
import java.io.File;

@Configuration
@ComponentScan(basePackages = "org.project.spring.telecom")
public class Application {

    public static void main(String[] args) throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.addWebapp("/telecom", new File("webapp").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();
    }
}