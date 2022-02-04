package dev.niveth.verifica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.servlet.DispatcherServlet;


@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class VerificaApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(VerificaApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(VerificaApplication.class);
        ConfigurableEnvironment environment = new StandardEnvironment();
        String env = System.getProperty("spring.profiles.active","local");
        LOGGER.info(System.getProperty("spring.profiles.active","local"));
        environment.setActiveProfiles(env);
        application.setEnvironment(environment);
        application.run(args);
    }

}
