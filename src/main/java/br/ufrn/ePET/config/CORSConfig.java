package br.ufrn.ePET.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CORSConfig extends WebMvcConfigurerAdapter{
	
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET","POST", "PUT",
				"DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}

}
