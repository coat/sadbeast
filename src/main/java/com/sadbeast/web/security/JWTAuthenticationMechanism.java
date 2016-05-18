package com.sadbeast.web.security;

import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.SecurityContext;
import io.undertow.server.HttpServerExchange;

public class JWTAuthenticationMechanism implements AuthenticationMechanism {
    @Override
    public AuthenticationMechanismOutcome authenticate(HttpServerExchange exchange, SecurityContext securityContext) {
        securityContext.authenticationComplete(new SBAccount("coat", null), "jwt", false);
        return AuthenticationMechanismOutcome.AUTHENTICATED;
    }

    @Override
    public ChallengeResult sendChallenge(HttpServerExchange exchange, SecurityContext securityContext) {
        return new ChallengeResult(false);
    }
}
