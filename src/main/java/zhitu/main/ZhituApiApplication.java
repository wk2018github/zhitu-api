package zhitu.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import zhitu.filter.LoginFilter;

@SpringBootApplication
@ComponentScan(basePackages = {"zhitu"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,
//        MongoAutoConfiguration.class,
//        MongoDataAutoConfiguration.class})
public class ZhituApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZhituApiApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean loginFilterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean(new LoginFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
