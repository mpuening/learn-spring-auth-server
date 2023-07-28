package io.github.learnspringauthserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuth2ResourceConfiguration {

	@Bean
	@Order(3)
	public SecurityFilterChain oauth2ResourceSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.securityMatcher("/api/**")
				.oauth2ResourceServer(oauth2 -> 
						oauth2.jwt(Customizer.withDefaults())
				)
				.authorizeHttpRequests((authorize) ->
						authorize
							.requestMatchers("/api/me").permitAll()
							.anyRequest().authenticated()
				)
				.build();
	}
}
