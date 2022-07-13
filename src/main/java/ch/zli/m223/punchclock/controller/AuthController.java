package ch.zli.m223.punchclock.controller;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.AuthenticationService;

@Tag(name = "Authorization", description = "This endpoint can be used for authorization as a user")
@Path("/auth")
public class AuthController {
    @Inject
    AuthenticationService authenticationService; 

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(User user){

        if(authenticationService.checkIfUserExists(user)){
            return authenticationService.GenerateValidJwtToken(user.getUsername());
        }
        else{
            throw new NotAuthorizedException("Credentials for the user "+ user.getUsername() +" invalid");
        } 
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public void register(@Valid User user) {
        authenticationService.createNewUser(user);
    }
}
