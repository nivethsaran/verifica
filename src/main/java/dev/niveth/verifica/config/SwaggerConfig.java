package dev.niveth.verifica.config;

import dev.niveth.verifica.constants.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("dev.niveth.verifica"))
                    .paths(PathSelectors.any())
                    .build()
                    .tags(new Tag(Constants.APP_NAME,"Email Verification"))
                    .apiInfo(getApiInfo())
                    .groupName(Constants.APP_NAME);
        }

    private ApiInfo getApiInfo() {
            return new ApiInfo(
                    Constants.APP_NAME,
                    "Email Validation",
                    "1.0",
                    "urn:tos",
                    new Contact("Developer" , "https://nivethsaran.github.io", "nivethsarandev@gmail.com"),
                    "MIT License",
                    "https://github.com/nivethsaran/verifica/blob/main/LICENSE",
                    new ArrayList<>()
            );
    }

}
