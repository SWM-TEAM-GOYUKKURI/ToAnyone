package anyone.to.soma.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Value("${swagger.host:api.toanyone.im}")
    private String host;
    @Value("${swagger.protocol:https}")
    private String protocol;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .host(host)
                .protocols(Collections.singleton(protocol))
                .select()
                .apis(RequestHandlerSelectors.basePackage("anyone.to"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("To Anyone API Docs")
                .version("1.0.0")
                .description("To Anyone API Docs")
                .build();
    }
}
