package org.acme.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/authorization")
public class AuthorizationController {

    @GET
    private String generateToken(){
        return  "";
    }
}
