package com.example.as.configuration;

import java.util.Collection;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
					authorize.requestMatchers("/login","/","/css/**","/register").permitAll();
					authorize.requestMatchers("/admin/**").hasAuthority("ADMIN");
					authorize.requestMatchers("/user/**").hasAuthority("USER");
					authorize.anyRequest().authenticated();
					System.out.println("In authorization");
//					authorize.anyRequest().permitAll();
				})
				.formLogin(formLogin -> formLogin.loginPage("/login").loginProcessingUrl("/login").successHandler((req, resp, authentication) -> {
					System.out.println(authentication.toString());
					System.out.println(authentication.getName());
					System.out.println(authentication.getAuthorities().toString());
					System.out.println(authentication.getName());
					System.out.println(authentication.getAuthorities().size());
					
					
					Set<String> roleSet= AuthorityUtils.authorityListToSet(authentication.getAuthorities());
				    
				    if(roleSet.contains("ADMIN"))
				    {
				    	resp.sendRedirect("/admin/");
				    }
				    else 
				    {
				    	resp.sendRedirect("/user/");
				    }
				})
				)
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
}
