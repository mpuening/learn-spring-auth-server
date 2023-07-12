package io.github.learnspringauthserver.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/webjars/**", "/assets/**");
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			ClientRegistrationRepository clientRegistrationRepository) throws Exception {
		http
			.oauth2Client(withDefaults())
			.oauth2Login(oauth2Login ->
					oauth2Login
						.loginPage("/oauth2/authorization/app-client")
						//.userInfoEndpoint(c -> {
						//	c.userService(new DefaultOAuth2UserService());
						//})
						)
			.logout(logout ->
					logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)))
			.authorizeHttpRequests(authorize ->
					authorize
						.requestMatchers("/", "/index", "/logged-out").permitAll()
						.anyRequest().authenticated());
		return http.build();
	}

	private LogoutSuccessHandler oidcLogoutSuccessHandler(
			ClientRegistrationRepository clientRegistrationRepository) {
		OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
				new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/logged-out");
		return oidcLogoutSuccessHandler;
	}

}