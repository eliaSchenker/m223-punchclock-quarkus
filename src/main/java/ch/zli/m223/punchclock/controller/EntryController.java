package ch.zli.m223.punchclock.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.EntryService;
import ch.zli.m223.punchclock.service.UserService;
import io.quarkus.security.runtime.SecurityIdentityAssociation;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @Inject
    UserService userService;

    @Inject
    SecurityIdentityAssociation identity;

    /**
     * Returns a list of the users entries
     * Only available to logged in users
     * @return List of entries
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User", "Admin"})
    @Operation(summary = "List all Entries of the current logged in User", description = "")
    public List<Entry> list() {
        User loggedInUser = userService.getUserByUsername(identity.getIdentity().getPrincipal().getName());
        return loggedInUser.getEntries();
    }

    /**
     * Creates a new entry, the owner (user) is automatically assigned using the logged in user
     * Only available to logged in users
     * @param entry The entry which should be added
     * @return The new entry
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User", "Admin"})
    @Operation(summary = "Add a new Entry", description = "The newly created entry is returned. The id may not be passed.")
    public Entry add(@Valid Entry entry) {
        User loggedInUser = userService.getUserByUsername(identity.getIdentity().getPrincipal().getName());
        entry.setUser(loggedInUser);
        return entryService.createEntry(entry);
    }

    /**
     * Updates an entry
     * Only available to the owner of the entry
     * @param entry The entry containing new information
     * @return The edited entry
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User", "Admin"})
    @Operation(summary = "Updates an entry", description = "The updated entry is returned")
    public Entry update(@Valid Entry entry) {
        User loggedInUser = userService.getUserByUsername(identity.getIdentity().getPrincipal().getName());
        Entry foundEntry = entryService.findById(entry.getId()); //Get a copy of the entry in the database to confirm that the user has rights to edit it
        
        if(foundEntry.getUser().getUsername() == loggedInUser.getUsername()) {
            return entryService.updateEntry(entry);
        }else {
            throw new NotAuthorizedException("User not authorized to update this entry");
        }
      
    }

    /**
     * Deletes an existing entry
     * Only available to the owner of the entry
     * @param id The id of the entry 
     * @return The deleted entry
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User", "Admin"})
    @Operation(summary = "Deletes an entry", description = "The deleted entry is returned")
    @Path("/{id}")
    public Entry delete(@PathParam("id") long id) {
        User loggedInUser = userService.getUserByUsername(identity.getIdentity().getPrincipal().getName());
        Entry foundEntry = entryService.findById(id); //Get a copy of the entry in the database to confirm that the user has rights to edit it
        
        if(foundEntry.getUser().getUsername() == loggedInUser.getUsername()) {
            return entryService.deleteEntry(id);
        }else {
            throw new NotAuthorizedException("User not authorized to delete this entry");
        }
    }
}
