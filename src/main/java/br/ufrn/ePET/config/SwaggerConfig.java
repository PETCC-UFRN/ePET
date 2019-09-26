package br.ufrn.ePET.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}
	
	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfo("ePET", "End-points do sistema do ePET", "1.0", "", "petcc@dimap.ufrn.br", "PET-CC", "http://petcc.dimap.ufrn.br/");
	}
}
