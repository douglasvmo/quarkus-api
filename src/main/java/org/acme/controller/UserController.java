package org.acme.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.User;
import org.acme.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Path("/users")
public class UserController {

    public static class Person {
        @JsonProperty
        String name;
        @JsonProperty
        String email;
        @JsonProperty
        String password;
    }

    @Inject
    UserRepository repository;

    @GET()
    public Response listUser() {
        List<User> users = repository.listAll();
        return Response.ok(users).build();
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Person userToSave) {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setName(userToSave.name);
        user.setEmail(userToSave.email);
        user.setPassword(userToSave.password);
        repository.persist(user);

        return Response.ok(user).build();
    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UUID uuid, Person person) {
        User user = repository.findByUUId(uuid);

        if (user == null) {
            throw new NotFoundException();
        }

        user.setName(person.name);
        user.setEmail(person.email);
        user.setPassword(person.password);
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
