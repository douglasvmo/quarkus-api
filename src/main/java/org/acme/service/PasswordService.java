package org.acme.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordService {

    public String hash(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    public boolean verify(String plain, String hashed){
        return BcryptUtil.matches(plain, hashed);
    }
}
