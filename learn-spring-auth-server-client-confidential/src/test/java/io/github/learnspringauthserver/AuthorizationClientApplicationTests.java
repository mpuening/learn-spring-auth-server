package io.github.learnspringauthserver;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Requires auth server to be running. How to test without it?")
@SpringBootTest
public class AuthorizationClientApplicationTests {

	@Test
	public void contextLoads() {
	}

}
