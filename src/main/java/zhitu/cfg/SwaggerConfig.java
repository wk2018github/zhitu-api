package zhitu.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("zhitu"))
                .paths(PathSelectors.any())
                .build();
    }

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("用于构建RESTful API")
                .description("本系统用于北京数起科技有限责任公司")
                .termsOfServiceUrl("http://www.louddt.com")
                .version("1.0")
                .build();
    }
}
