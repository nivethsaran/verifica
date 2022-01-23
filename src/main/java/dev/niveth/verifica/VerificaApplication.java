package dev.niveth.verifica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;


@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class VerificaApplication {

    public static final Logger LOGGER = LoggerFactory.getLogger(VerificaApplication.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(VerificaApplication.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet) context.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        LOGGER.info(System.getProperty("spring.profiles.active"));
    }

}
