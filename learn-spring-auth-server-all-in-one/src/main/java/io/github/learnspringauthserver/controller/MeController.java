package io.github.learnspringauthserver.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

	@GetMapping("/api/me")
	public Map<String, Object> me(Authentication authentication) {
		Map<String, Object> response = Map.of("anonymous", true);
		if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
			Jwt token = (Jwt) authentication.getPrincipal();
			System.out.println("Resource 1 received token: " + token.getTokenValue());
			response = Map.of(

					"sub", token.getSubject(),

					"aud", token.getAudience(),

					"fullName", token.getClaim("fullName"),

					"groups", token.getClaimAsStringList("groups"),

					"expiresIn", (token.getExpiresAt().toEpochMilli() - System.currentTimeMillis()) / 1000

			);
		}
		return response;
	}

	@GetMapping("/api/me2")
	public Map<String, Object> me2(Authentication authentication) {
		return me(authentication);
	}
}
