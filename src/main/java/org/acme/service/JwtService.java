package org.acme.service;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.algorithm.SignatureAlgorithm;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.User;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class JwtService {
    @Inject
    Jwt jwt;

    public String buildToken(User user) {
        return Jwt.claims()
                .claim("customClaim", "customValue")
                .claim("user", user.getName())
                .claim("uuid", user.getUuid())
                .expiresIn(3600)
                .jws()
                .algorithm(SignatureAlgorithm.HS256)
                .sign("cat");
    }
}
