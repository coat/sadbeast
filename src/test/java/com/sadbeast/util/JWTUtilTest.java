package com.sadbeast.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sadbeast.dto.UserDto;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class JWTUtilTest {
    @Test
    public void tokenShouldBeCreatedCorrectly() throws ParseException, JOSEException {
        UserDto user = new UserDto(1L, "");

        String tokenString = JWTUtil.createJWT(user);

        assertNotNull(tokenString);


        JWSVerifier verifier = new MACVerifier(ConfigFactory.load().getString("jwt.secret"));

        SignedJWT signedJWT = SignedJWT.parse(tokenString);
        assertTrue(signedJWT.verify(verifier));

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        assertThat(claims.getAudience(), contains("usr"));
        assertEquals(user.getId().toString(), claims.getSubject());
    }
}
