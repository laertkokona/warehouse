package al.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import al.example.enums.RoleEnum;
import al.example.exception.CustomAccessDeniedHandler;
import al.example.filter.CustomAuthenticationFilter;
import al.example.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

//	private final PasswordEncoder passwordEncoder;
//	private final UserService userService;
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userService).passwordEncoder(passwordEncoder.encoder());
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.and().authorizeRequests().antMatchers("/v3/api-docs", "/swagger-ui/**", "/swagger-ui*/**", "/warehouse-api/**").permitAll()
//			.and().authorizeRequests().anyRequest().permitAll()
//			.and().addFilter(new CustomAuthenticationFilter(authenticationManagerBean()))
//				.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
//
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests().antMatchers("/v3/api-docs", "/swagger-ui/**", "/swagger-ui*/**", "/warehouse-api/**", "/login").permitAll()
		.antMatchers("/users/**").hasAuthority(RoleEnum.SYSTEM_ADMIN.name())
		.and().authorizeRequests().anyRequest().authenticated()
		.and().addFilter(new CustomAuthenticationFilter(authManager(http.getSharedObject(AuthenticationConfiguration.class))))
			.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		return http.build();

	}

}
