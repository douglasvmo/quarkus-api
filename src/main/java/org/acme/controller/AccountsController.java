package org.acme.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.Account;
import org.acme.repository.AccountRepository;
import org.acme.service.PasswordService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/accounts")
public class AccountsController {

    public static class NewAccount {
        @JsonProperty
        String name;
        @JsonProperty
        String email;
        @JsonProperty
        String password;
    }

    public static class UpdateAccount {
        Optional<String> name;
        Optional<String> email;
        Optional<String> password;
    }

    @Inject
    AccountRepository repository;

    @Inject
    PasswordService passwordService;

    @GET()
    @RolesAllowed("ADMIN")
    public Response listUser() {
        List<Account> users = repository.listAll();
        return Response.ok(users).build();
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(NewAccount body) {
        Account user = new Account();
        user.setUuid(UUID.randomUUID());
        user.setName(body.name);
        user.setEmail(body.email);
        user.setPassword(passwordService.hash(body.password));

        repository.persist(user);

        return Response.ok(user).build();
    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UUID uuid, UpdateAccount body) {
        Account user = repository.findByUUId(uuid);

        if (user == null) {
            throw new NotFoundException();
        }

        body.name.ifPresent(user::setName);
        body.email.ifPresent(user::setEmail);
        body.password.ifPresent(pwd -> user.setPassword(passwordService.hash(pwd)));
        repository.persist(user);

        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response deleteUser(@PathParam("uuid") UUID uuid) {
        long rows = repository.deleteByUUid(uuid);

        if (rows == 0) {
            throw new NotFoundException();
        }

        return Response.ok().build();
    }
}
