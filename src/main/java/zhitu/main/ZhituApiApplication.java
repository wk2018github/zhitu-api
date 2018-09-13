package zhitu.main;

import java.util.Properties;


import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.github.pagehelper.PageHelper;

import zhitu.filter.LoginFilter;

@SpringBootApplication
@ComponentScan(basePackages = {"zhitu"})
/*@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class})*/
@MapperScan("zhitu.sq.dataset.mapper")
public class ZhituApiApplication {
    public static void main(String[] args) {
    	//1.初始化
//        SpringApplication application=  new SpringApplication(ZhituApiApplication.class);
//    	//2.添加数据源
//        Map<String, Object> map = new HashMap<>();
//        map.put("spring.datasource.url","jdbc:mysql://localhost:3306/dk_kg?useSSL=false");
//        map.put("spring.datasource.username","root");
//        map.put("spring.datasource.password","123456");
//
//        //3.开启驼峰映射 (Such as account_id ==> accountId)
//        map.put("mybatis.configuration.map-underscore-to-camel-case",true);
//        application.setDefaultProperties(map);
//        //4.启动应用
//        application.run(args);

        SpringApplication.run(ZhituApiApplication.class, args);
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
    public FilterRegistrationBean loginFilterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean(new LoginFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
    
    @Bean
    PageHelper pageHelper(){
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
 
        //添加插件
        new SqlSessionFactoryBean().setPlugins(new Interceptor[]{pageHelper});
        return pageHelper;
    }
    
    /**  
     * 文件上传配置  
     * @return  
     
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("10240KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }  */  
}
