package com.sadbeast.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sadbeast.dto.UserDto;
import com.typesafe.config.ConfigFactory;
import io.undertow.server.HttpServerExchange;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class JWTUtil {
    private static final String COOKIE_KEY = "auth";
    private static final String JWT_SECRET = ConfigFactory.load().getString("jwt.secret");
    private static final JWSHeader HEADER = new JWSHeader(JWSAlgorithm.HS256);

    private static JWSVerifier VERIFIER;
    private static JWSSigner SIGNER;

    static {
        try {
            VERIFIER = new MACVerifier(JWT_SECRET);
            SIGNER = new MACSigner(JWT_SECRET);
        } catch (JOSEException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private JWTUtil() {
    }

    public static String createJWT(final UserDto user) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .audience("usr")
                    .issueTime(new Date())
                    .jwtID(UUID.randomUUID().toString())
                    .issuer("https://www.sadbeast.com")
                    .subject(user.getId().toString())
                    .build();

            SignedJWT signedJWT = new SignedJWT(HEADER, claims);
            signedJWT.sign(SIGNER);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifiedToken(final HttpServerExchange exchange) {
        return exchange.getRequestCookies().containsKey(COOKIE_KEY)
                && verifiedToken(exchange.getRequestCookies().get(COOKIE_KEY).getValue());
    }

    static boolean verifiedToken(final String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return signedJWT.verify(VERIFIER);
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getSubject(final HttpServerExchange exchange) {
        if (exchange.getRequestCookies().containsKey(COOKIE_KEY)) {
            return getSubject(exchange.getRequestCookies().get(COOKIE_KEY).getValue());
        }
        return null;
    }

    public static String getSubject(final String token) {
        if (verifiedToken(token)) {
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);

                return signedJWT.getJWTClaimsSet().getSubject();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
