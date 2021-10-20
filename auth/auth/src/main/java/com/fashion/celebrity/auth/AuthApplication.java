package com.fashion.celebrity.auth;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(value= {"com.fashion.celebrity.**.mapper"})
public class AuthApplication {
	private static final String CLASSPATH_MAPPER_LOCATION = "classpath:/mappers/*.xml";
	private static final String CLASSPATH_MAPPER_CONFIG_LOCATION = "classpath:/mybatis-config.xml";

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(CLASSPATH_MAPPER_LOCATION));
		sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CLASSPATH_MAPPER_CONFIG_LOCATION));
		sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return sessionFactory.getObject();
	}

}
