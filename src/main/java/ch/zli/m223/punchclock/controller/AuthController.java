package ch.zli.m223.punchclock.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.AuthenticationService;
import ch.zli.m223.punchclock.service.UserService;

@Path("/auth")
@Tag(name = "Authorization", description = "Handles the User authorization")
public class AuthController {
    @Inject
    AuthenticationService authenticationService;
    
    @Inject
    UserService userService;

    /**
     * Logs in a user using a username and a password
     * Available to everyone
     * @return If the Authentication is successfull, a JWA token is returned
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(User user){
        if(authenticationService.checkUserCredentials(user)){
            //Get the user from the database (with correct roles)
            User databaseUser = userService.getUserByUsername(user.getUsername());
            return authenticationService.GenerateValidJwtToken(databaseUser);
        }
        else{
            throw new NotAuthorizedException("Credentials for the user " + user.getUsername() + " invalid ");
        } 
    }
}
