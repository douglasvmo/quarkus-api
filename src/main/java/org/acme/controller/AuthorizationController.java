package org.acme.controller;


import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.repository.AccountRepository;
import org.acme.service.JwtService;
import org.acme.service.PasswordService;

@Path("/authorization")
public class AuthorizationController {

    public record Login(String email, String password) {
    }

    private record TokenResponse(String token) {
    }

    @Inject
    AccountRepository repository;

    @Inject
    PasswordService passwordService;

    @Inject
    JwtService jwtService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authorization(Login login) {
        var user = repository.findByEmail(login.email);

        if(user == null) throw new NotFoundException();

        if (passwordService.verify(login.password, user.getPassword())) {
            String token = jwtService.generateJwt(user);
            TokenResponse body = new TokenResponse(token);

            return Response.ok(body).build();
        } else {
            throw new NotFoundException();
        }
    }
}
