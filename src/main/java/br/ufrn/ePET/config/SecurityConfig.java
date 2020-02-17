package br.ufrn.ePET.config;

import br.ufrn.ePET.security.JWTAutheticationFilter;
import br.ufrn.ePET.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import br.ufrn.ePET.service.CustomUserDetailsService;

import static br.ufrn.ePET.security.SecurityConstants.SIGN_UP_URL;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private  CustomUserDetailsService userDetailsService;
	
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
		 http.cors().and().csrf().disable().authorizeRequests()
				 .antMatchers(HttpMethod.GET, SIGN_UP_URL).permitAll()
				 .and()
				 .formLogin()
				 .and()
				 .addFilter(new JWTAutheticationFilter(authenticationManager()))
				 .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/service/api/usuarios-cadastrar/**", "/service/swagger-ui.html#/**");
	}
	
	
	
	
}
