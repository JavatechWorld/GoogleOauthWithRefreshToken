package com.spring.implementation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/auth/google")
	public String initiateOAuth2Flow() {
		// Redirect to Google authentication
		return "redirect:/oauth2/authorization/google";
	}

	@GetMapping("/auth/google/callback")
	public String handleGoogleCallback(@AuthenticationPrincipal OAuth2AuthenticationToken authenticationToken) {
		if (authenticationToken == null) {
			return "Authentication failed.";
		}

		// Get the authorized client for the Google OAuth2 provider
		OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
				authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());

		if (authorizedClient != null) {
			String refreshTokenValue = authorizedClient.getRefreshToken().getTokenValue();
			return "Refresh Token: " + refreshTokenValue;
		} else {
			return "Authorized client not found.";
		}
	}

	@PostMapping("/token/refresh")
	public String refreshAccessToken(@RequestParam("refresh_token") String refreshToken) {
		// Implement token refresh logic here
		String newAccessToken = "new-access-token"; // Replace with actual token
		return "New access token generated: " + newAccessToken;
	}
}
