package com.koopey.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SwaggerConfiguration {
    
  @Bean
	public GroupedOpenApi usersGroup(@Value("${springdoc.version}") String appVersion) {
		return GroupedOpenApi.builder().group("users")
				.addOperationCustomizer((operation, handlerMethod) -> {
					operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
					return operation;
				})
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Users API").version(appVersion)))
				.packagesToScan("org.springdoc.demo.app2")
				.build();
	}

}
