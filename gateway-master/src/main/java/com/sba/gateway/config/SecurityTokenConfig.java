package com.sba.gateway.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sba.gateway.filter.JwtTokenAuthenticationFilter;

@EnableWebSecurity 	// Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtConfig jwtConfig;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		final String[] SWAGGER_UI = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**",
				"/actuator/**", "/account/v2/api-docs" };

		web.ignoring().antMatchers(SWAGGER_UI);
	}
 
	@Override
  	protected void configure(HttpSecurity http) throws Exception {
    	   http
    	   .cors().and()
		.csrf().disable()
		    // make sure we use stateless session; session won't be used to store user's state.
	 	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 	
		.and()
		    // handle an authorized attempts 
		    .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)) 	
		.and()
		   // Add a filter to validate the tokens with every request
		   .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
		// authorization requests config
		.authorizeRequests()
		   // allow all who are accessing "auth" service
		   .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
		   .antMatchers(HttpMethod.POST, "/account/api/v1/add").permitAll() 
		   .antMatchers(HttpMethod.GET, "/course/api/v1/mentor/list").permitAll()
//		   .antMatchers(HttpMethod.GET, "/account/api/v1/query").permitAll()
		   .antMatchers(HttpMethod.GET, "/course/api/v1/mentor/book").hasRole("user")
		   .antMatchers(HttpMethod.GET, "/course/api/v1/user/list").hasRole("user")
		   .antMatchers(HttpMethod.GET, "/course/api/v1/mentor/searchcourse").hasRole("user")
		   // must be an admin if trying to access admin area (authentication is also required here)
		   .antMatchers("/account/**", "/course/**", "/mentor/**").hasAnyRole("admin","mentor")
//		   .antMatchers(HttpMethod.POST, "/course/**", "/account/**").hasAnyRole("admin","mentor")
		   // Any other request must be authenticated
		   .anyRequest().authenticated(); 
	}
	
	@Bean
  	public JwtConfig jwtConfig() {
    	   return new JwtConfig();
  	}
}
