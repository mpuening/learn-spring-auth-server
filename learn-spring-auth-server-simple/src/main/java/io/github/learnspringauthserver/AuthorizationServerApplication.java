package io.github.learnspringauthserver;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	/**
	 * Add a role claim to access token
	 */
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			if (context.getTokenType() == OAuth2TokenType.ACCESS_TOKEN) {
				Authentication principal = context.getPrincipal();
				Set<String> authorities = principal.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.map(role -> role.replace("ROLE_", ""))
						.collect(Collectors.toSet());
				context.getClaims().claim("roles", authorities);
			}
		};
	}
}