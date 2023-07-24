package com.ec.prontiauto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
//@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
	 public static final String AUTHORIZATION_HEADER = "Authorization";
	    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	@Bean
	public Docket createRestApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.apiInfo(apiInfo()).select()
//				.apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build();
		 return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .securityContexts(Arrays.asList(securityContext()))
	                .securitySchemes(Arrays.asList(apiKey()))
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.regex("/api/.*"))
	                .build();

	}
	
	private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

	private ApiInfo apiInfo() {
//		Contact contact = new Contact("ProntiAuto", "www.deckxel.com", "decxel@hotmail.com");
//
//		return new ApiInfoBuilder().title("Documentacion con Swagger").description("Informacion adicional sobre el API")
//				.termsOfServiceUrl("www.deckxel.com").contact(contact).version("1.0").build();
		return new ApiInfo(
                "ProntiAuto",
                "Descripción",
                "2.0",
                "Términos y Condiciones",
                new Contact("Darwin Morocho", "www.deckxel.com", "darwinvinicio14_11@hotmail.com"),
                "Licencia",
                "www.deckxel.com",
                Collections.emptyList()
        );

	}

}
