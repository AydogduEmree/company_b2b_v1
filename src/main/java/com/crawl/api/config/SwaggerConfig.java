package com.crawl.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	//http://localhost:8080/swagger-ui.html#/
	@Bean
	public Docket b2bCompanyApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		// committed
		return new ApiInfoBuilder()
				.title("B2B Company API")
				.version("1.0")
				.description("API for Web Application")
				.contact(new Contact("Emre Aydoğdu", "https://www.linkedin.com/in/emreaydogdu1/", "aydogduemre23@gmail.com"))
				.license("Apache License Version 2.0")
				.build();
	}
}
