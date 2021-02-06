package br.ufrn.ePET.config;


import br.ufrn.ePET.security.JwtTokenFilterConfigurer;
import br.ufrn.ePET.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import br.ufrn.ePET.service.CustomUserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static br.ufrn.ePET.security.SecurityConstants.SIGN_IN_URL;
import static br.ufrn.ePET.security.SecurityConstants.SIGN_UP_URL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*@Autowired
	private  CustomUserDetailsService userDetailsService;*/

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().csrf().disable()
		.addFilterBefore(new CORSConfig(), ChannelProcessingFilter.class)
		.logout().logoutSuccessUrl("/logout");
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 //http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and().csrf().disable();
		 http.csrf().disable();
		 
		 http.cors();

		 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		 http.authorizeRequests()
				 .antMatchers(HttpMethod.POST, SIGN_IN_URL).permitAll()
				 .antMatchers("/v2/**").permitAll()
				 .antMatchers("/swagger-ui.html").permitAll()
				 .antMatchers("/swagger-resources/**").permitAll()
				 .antMatchers("/webjars/**").permitAll()
				 .antMatchers("/configuration/ui/**").permitAll()
				 .antMatchers("/configuration/security/**").permitAll()
				 .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
				 .antMatchers("/api/validation/**").permitAll()
				 .antMatchers("/api/reset/**").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/forgot").permitAll()
				 .antMatchers(HttpMethod.GET,"/api/informacoes").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/noticia").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/noticia/**").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/noticias-atuais").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/petianos-atuais").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/petianos-antigos").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/eventos-abertos").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/eventos-abertos/**").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/tutorias/{id}").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/tutorias").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/downloadfile/{filename:.+}").permitAll()
				 .antMatchers(HttpMethod.GET, "/downloadfile/{filename:.+}").permitAll()
				 .antMatchers(HttpMethod.POST, "/api/pagseguro-notificacao/**").permitAll()
				 .antMatchers(HttpMethod.GET, "/api/certificado/validar/{hash}").permitAll()
				 .antMatchers("/api/redirecionamento-pagamento/**").permitAll()
				 .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
				 .anyRequest().authenticated();

		 //http.exceptionHandling().accessDeniedHandler("/login");
		 http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
	}

	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}*/

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/swagger-resources/**")//
				.antMatchers("/swagger-ui.html");
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.applyPermitDefaultValues();
		corsConfig.addAllowedMethod(HttpMethod.PUT);
		corsConfig.addAllowedMethod(HttpMethod.DELETE);
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
	
}
