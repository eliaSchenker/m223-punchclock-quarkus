package ch.zli.m223.punchclock.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.UserService;
import io.quarkus.security.runtime.SecurityIdentityAssociation;

/**
 * UserController
 * The UserController contains various methods which provide a way to manage users
 * and return the current user
 * 
 * Author: Elia Schenker
 * Last change: 15.07.2022
 */
@Path("/users")
@Tag(name = "Users", description = "Provides various methods for the user model class")
public class UserController {
    
    @Inject
    UserService userService;

    @Inject
    SecurityIdentityAssociation identity;

    /**
     * Returns the user which was passed in the JWT-Authentication
     * Available to all logged in users
     * @return User object including the users role, location and username
     */
    @GET
    @Path("/currentuser")
    @RolesAllowed({"User", "Admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public User getCurrentUser(){
        return userService.getUserByUsername(identity.getIdentity().getPrincipal().getName());
    }

    /**
     * Returns a list of users
     * Only available to administrators
     * @return A complete list of the existing users
     */
    @GET
    @RolesAllowed("Admin")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.findAll();
    }

    /**
     * Adds a new user
     * Only available to administrators
     * @param user The user to be added
     * @return The created user
     */
    @POST
    @RolesAllowed("Admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(@Valid User user) {
        return userService.createNewUser(user);
    }

    /**
     * Edits a user
     * Only available to administrators
     * @param user The user object containing the new user information
     * @return The edited user object
     */
    @PUT
    @RolesAllowed("Admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@Valid User user) {
        return userService.updateUser(user);
    }

    /**
     * Deletes a user using an id
     * Only available to administrators
     * @param id The id of the user
     * @return The deleted user
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    public User deleteUser(@PathParam("id") long id) {
        return userService.deleteUser(id);
    }
}
