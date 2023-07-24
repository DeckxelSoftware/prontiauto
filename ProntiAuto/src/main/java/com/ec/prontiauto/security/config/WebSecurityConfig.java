package com.ec.prontiauto.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ec.prontiauto.security.JwtAuthenticationEntryPoint;
import com.ec.prontiauto.security.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${auth.enabled}")
	private boolean authEnabled;

	@Autowired
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		if (authEnabled) {
			httpSecurity.cors().and().csrf().disable()
					.authorizeRequests()
					.antMatchers(
							"/api/auth/**",
							"/v2/api-docs/**",
							"/swagger-ui/**",
							"/swagger-resources/**",
							"/configuration/**")
					.permitAll()
					.anyRequest().authenticated()
					.and()
					.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
					.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		} else {
			httpSecurity.cors().and().csrf().disable()
					.authorizeRequests()
					.antMatchers("/api/**",
							"/api/auth/**",
							"/v2/api-docs/**",
							"/swagger-ui/**",
							"/swagger-resources/**",
							"/configuration/**")
					.permitAll()
					.anyRequest().authenticated()
					.and()
					.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
					.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		}
	}

}