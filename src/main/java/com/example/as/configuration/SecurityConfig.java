package com.example.as.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.as.service.UserService;

@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authManager(UserService userDetailsService) {
		
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(daoProvider);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.
				csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize ->
				{	
				authorize.requestMatchers("/login","/","/css/**").permitAll();
				authorize.requestMatchers("/admin/").hasAuthority("ADMIN");
				authorize.requestMatchers("/user/").hasAuthority("USER");
				authorize.anyRequest().authenticated();
				})
				.formLogin(formLogin -> formLogin.loginPage("/login").successHandler((req, resp, authentication) -> {
				if (authentication.getAuthorities().contains("ADMIN")) {
				resp.sendRedirect("/admin/");
				} else {
				resp.sendRedirect("/user/");
				}
				}))
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
}
