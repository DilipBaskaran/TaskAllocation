//package com.techmahindra.taskallocation.config;
//
//import javax.annotation.Resource;
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////@Configuration
////@EnableWebSecurity
//public class SecurityConfiguration {//extends WebSecurityConfigurerAdapter {
//	
//	@Resource(name="userService")
//	private UserDetailsService userDetailsService;
//
//	//@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests().antMatchers("/","/home","/login", "/logout").permitAll();
//		http.authorizeRequests().antMatchers("/cart").access("hasAnyRole('ROLE_USER')");
//		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//		http.authorizeRequests().and().formLogin()//
//		.loginProcessingUrl("/j_spring_security_check") // Submit URL
//		.loginPage("/login")//
//		.defaultSuccessUrl("/home")//
//		.failureUrl("/login?error=true")//
//		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder(){
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
//
//	@Transactional
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		//System.out.println(passwordEncoder().encode("test"));
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
//}
