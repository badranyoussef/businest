package org.util;

import java.text.ParseException;

import com.nimbusds.jwt.SignedJWT;

public class TokenUtils {




    public static String getUserIdFromToken(String token) throws ParseException {
        SignedJWT jwt = SignedJWT.parse(token);
        return jwt.getJWTClaimsSet().getClaim("email").toString();
    }
}
