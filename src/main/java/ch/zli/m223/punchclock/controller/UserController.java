package ch.zli.m223.punchclock.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.UserService;
import io.quarkus.security.runtime.SecurityIdentityAssociation;

@Path("/users")
@Tag(name = "Users", description = "Provides various methods for the user model class")
public class UserController {
    
    @Inject
    UserService userService;

    @Inject
    SecurityIdentityAssociation identity;

    @GET
    @Path("/currentuser")
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public User getCurrentUser(){
        return userService.getUserByUsername(identity.getIdentity().getPrincipal().getName());
    }
}
