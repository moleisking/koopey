package com.koopey.api.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import static org.springdoc.core.Constants.ALL_PATTERN;

@OpenAPIDefinition(
    info = @Info(
        title = "Koopey API",
        version = "1.00",
        license = @License(name = "OSL-3.0", url = "https://opensource.org/licenses/OSL-3.0"),
        contact = @Contact(url = "http://koopey.com", name = "Scott Johnston", email = "moleisking@gmail.com")
    ), security = {
        @SecurityRequirement(
                name = "bearerAuth"
        )
    }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Authorization",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfiguration {
    

//	@Profile("!prod")
/*@Bean
	public GroupedOpenApi actuatorApi(OpenApiCustomiser actuatorOpenApiCustomiser,
			OperationCustomizer actuatorCustomizer,
			WebEndpointProperties endpointProperties,
			@Value("${springdoc.version}") String appVersion) {
		return GroupedOpenApi.builder()
				.group("Actuator")
				.pathsToMatch(endpointProperties.getBasePath()+ ALL_PATTERN)
				.addOpenApiCustomiser(actuatorOpenApiCustomiser)
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Actuator API").version(appVersion)))
				.addOperationCustomizer(actuatorCustomizer)
				.pathsToExclude("/health/*")
				.build();
	}*/


 /* @Bean
	public GroupedOpenApi usersGroup(@Value("${springdoc.version}") String appVersion) {
		return GroupedOpenApi.builder().group("users")
				.addOperationCustomizer((operation, handlerMethod) -> {
					operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
					return operation;
				})
				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Users API").version(appVersion)))
				.packagesToScan("org.springdoc.demo.app2")
				.build();
	}*/

}
