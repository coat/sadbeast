package com.sadbeast.web.security;

import com.sadbeast.util.JWTUtil;
import com.sadbeast.web.beans.LoginBean;
import io.undertow.Handlers;
import io.undertow.security.api.AuthenticationMechanism;
import io.undertow.security.api.SecurityContext;
import io.undertow.security.idm.PasswordCredential;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.Cookie;
import io.undertow.server.handlers.CookieImpl;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.server.session.Session;
import io.undertow.util.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Set;

import static com.sadbeast.web.security.SBAuthenticationConstraintHandler.AUTH_OPTIONAL_ATTACHMENT_KEY;

@Singleton
public class SBAuthenticationMechanism implements AuthenticationMechanism {
    public static final String LOCATION_ATTRIBUTE = SBAuthenticationMechanism.class.getName() + ".LOCATION";

    private static final String AUTHENTICATE_URL = "/authenticate";
    private static final String LOGIN_URL = "/login";

    private final Validator validator;
    private final SBIdentityManager identityManager;

    @Inject
    public SBAuthenticationMechanism(final Validator validator, final SBIdentityManager identityManager) {
        this.validator = validator;
        this.identityManager = identityManager;
    }

    @Override
    public AuthenticationMechanismOutcome authenticate(HttpServerExchange exchange, SecurityContext securityContext) {
        if (JWTUtil.verifiedToken(exchange)) {
            securityContext.authenticationComplete(new SBAccount(JWTUtil.getSubject(exchange), ""), "sb", true);

            return AuthenticationMechanismOutcome.AUTHENTICATED;
        } else {
            if (exchange.getRequestURI().endsWith(AUTHENTICATE_URL) && exchange.getRequestMethod().equals(Methods.POST)) {
                FormDataParser parser = FormParserFactory.builder().build().createParser(exchange);

                try {
                    FormData formData = parser.parseBlocking();
                    String username = formData.getFirst("username").getValue();
                    String password = formData.getFirst("password").getValue();

                    LoginBean loginBean = new LoginBean(username, password);

                    Set<ConstraintViolation<LoginBean>> violations = validator.validate(loginBean);
                    if (violations.isEmpty()) {
                        SBAccount account = (SBAccount) identityManager.verify(username, new PasswordCredential(password.toCharArray()));
                        if (account != null) {
                            securityContext.authenticationComplete(account, "sb", true);
                            exchange.getResponseCookies().put("auth", buildAuthCookie(account.getToken()));
//                            handleRedirectBack(exchange);
                            exchange.dispatch(Handlers.redirect("/"));

                            return AuthenticationMechanismOutcome.AUTHENTICATED;
                        }
                    }
                    Session session = Sessions.getOrCreateSession(exchange);

                    session.setAttribute("errors", violations);
                    session.setAttribute("username", username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // whitelisted URL
                return AuthenticationMechanismOutcome.NOT_ATTEMPTED;
            }
            return AuthenticationMechanismOutcome.NOT_AUTHENTICATED;
        }
    }

    protected void handleRedirectBack(final HttpServerExchange exchange) {
        final Session session = Sessions.getSession(exchange);
        if (session != null) {
            final String location = (String) session.removeAttribute(LOCATION_ATTRIBUTE);
            if (location != null) {
/*                exchange.addDefaultResponseListener(e -> {
                    SBAuthenticationMechanism.sendRedirect(e, location);
                    e.setStatusCode(StatusCodes.FOUND);
                    e.endExchange();
                    return true;
                });*/
                servePage(exchange, location);
                return;
            }
        }
        servePage(exchange, "/admin");
    }

    @Override
    public ChallengeResult sendChallenge(HttpServerExchange exchange, SecurityContext securityContext) {
//        Boolean authOptional = exchange.getAttachment(AUTH_OPTIONAL_ATTACHMENT_KEY);
//        if (authOptional != null && authOptional) {
//            return new ChallengeResult(true);
//        }

        if (exchange.getRequestURI().endsWith(AUTHENTICATE_URL) && exchange.getRequestMethod().equals(Methods.POST)) {
            // This method would no longer be called if authentication had already occurred.
            Integer code = servePage(exchange, LOGIN_URL);
            return new ChallengeResult(true, code);
        } else {
            // we need to store the URL
            storeInitialLocation(exchange);
            // TODO - Rather than redirecting, in order to make this mechanism compatible with the other mechanisms we need to
            // return the actual error page not a redirect.
            Integer code = servePage(exchange, LOGIN_URL);
            return new ChallengeResult(true, code);
        }
    }

    protected void storeInitialLocation(final HttpServerExchange exchange) {
        Session session = Sessions.getOrCreateSession(exchange);
        session.setAttribute(LOCATION_ATTRIBUTE, RedirectBuilder.redirect(exchange, exchange.getRelativePath()));
    }

    protected Integer servePage(final HttpServerExchange exchange, final String location) {
        sendRedirect(exchange, location);
        return StatusCodes.TEMPORARY_REDIRECT;
    }

    static void sendRedirect(final HttpServerExchange exchange, final String location) {
        exchange.setRequestMethod(Methods.GET);
        try {
            URI uri = new URI(exchange.getRequestScheme(), exchange.getHostAndPort() + location, null);
            exchange.getResponseHeaders().put(Headers.LOCATION, uri.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Cookie buildAuthCookie(final String token) {
        Cookie auth = new CookieImpl("auth", token);
        auth.setHttpOnly(true);
        auth.setExpires(Date.from(LocalDateTime.now().plusWeeks(1).toInstant(ZoneOffset.UTC)));

        return auth;
    }
}
