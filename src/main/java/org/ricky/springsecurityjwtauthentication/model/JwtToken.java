package org.ricky.springsecurityjwtauthentication.model;

public record JwtToken(String accessToken, String refreshToken) {
}