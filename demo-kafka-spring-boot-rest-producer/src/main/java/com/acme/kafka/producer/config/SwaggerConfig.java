package com.acme.kafka.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acme.kafka.producer.constant.SpringConfigConstant;

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
	public Docket apiDocket() {
		 return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(getApiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage(SpringConfigConstant.BASE_PACKAGE))
	                .paths(PathSelectors.ant("/kafka/*"))
	                .build();
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Kafka REST Producer API")
				.description("Kafka REST Producer Connector")
				.license("Acme License")
				.version("1.0").build();
	}

}
