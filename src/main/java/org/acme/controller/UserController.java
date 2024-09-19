package org.acme.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.panache.common.Parameters;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.model.User;

import java.util.List;
import java.util.UUID;

@Path("/users")
@Transactional
public class UserController {

    public static class Person {
        @JsonProperty String name;
        @JsonProperty String email;
        @JsonProperty String password;
    }

    @GET()
    public Response listUser(){
        List<User> users = User.listAll();
        return Response.ok(users).build();
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(Person userToSave){
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setName(userToSave.name);
        user.setEmail(userToSave.email);
        user.setPassword(userToSave.password);
        user.persist();

        return Response.ok(user).build();
    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UUID uuid, Person person) {
      User user = User.find("uuid = :uuid", Parameters.with("uuid", uuid)).firstResult();

        if(user == null) {
            throw new NotFoundException();
        }

        user.setName(person.name);
        user.setEmail(person.email);
        user.setPassword(person.password);
        user.persist();

        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{uuid}")
    public Response deleteUser(@PathParam("uuid") UUID uuid){
     long rows = User.delete("uuid = :uuid", Parameters.with("uuid", uuid));

     if(rows == 0) {
         throw new NotFoundException();
     }

     return Response.ok().build();
    }
}
