package org.acme.service;


import io.smallrye.jwt.algorithm.SignatureAlgorithm;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.util.KeyUtils;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Account;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@ApplicationScoped
public class JwtService {
    private static final String[] GROUPS = {"CLIENT", "SALES", "OFFICE", "ADMIN"};

    private Set<String> parseAccess(byte access) {
        Set<String> groups = new HashSet<>();

        for (int i = 0; i < GROUPS.length; i++) {
            if ((access & (1 << i)) != 0) {
                groups.add(GROUPS[i]);
            }
        }
        return groups;
    }


    public String generateJwt(Account account) {
            return Jwt.claim("sub", account.getUuid().toString())
                    .claim("name", account.getName())
                    .groups(parseAccess(account.getAccess()))
                    .expiresIn(3600)
                    .sign();
    }
}
