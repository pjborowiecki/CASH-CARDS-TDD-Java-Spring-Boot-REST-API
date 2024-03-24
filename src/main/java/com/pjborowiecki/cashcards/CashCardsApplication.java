package com.pjborowiecki.cashcards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import com.pjborowiecki.cashcards.auth.AuthEntryPoint;

@SpringBootApplication
public class CashCardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashCardsApplication.class, args);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, AuthEntryPoint authEntryPoint) throws Exception {
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers(HttpMethod.GET, "/api/v1/cashcards/**")
						.hasAuthority("SCOPE_cashcard:read")
						.requestMatchers("/api/v1/cashcards/**")
						.hasAuthority("SCOPE_cashcard:write")
						.anyRequest()
						.authenticated())
				.oauth2ResourceServer((oauth2) -> oauth2
						.authenticationEntryPoint(authEntryPoint)
						.jwt(Customizer.withDefaults()));

		return http.build();
	}

}
