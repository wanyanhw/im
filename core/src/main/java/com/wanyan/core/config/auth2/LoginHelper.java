package com.wanyan.core.config.auth2;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

@Component
public class LoginHelper {

    private final String SECRET_KEY = "wyhwlml";

    private final String ISSUER = "wyhw";

    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    public String getToken(String username, String password) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withAudience(username, password)
                .sign(ALGORITHM);
    }

    public boolean verifyToken(String token) {
        Verification require = JWT.require(ALGORITHM);
        JWTVerifier build = require.withIssuer(ISSUER).build();
        DecodedJWT decodedJWT = build.verify(token);
        return decodedJWT.getAudience().size() > 0;
    }

}
