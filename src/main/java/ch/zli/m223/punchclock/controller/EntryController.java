package ch.zli.m223.punchclock.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "List all Entries", description = "")
    public List<Entry> list() {
        return entryService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a new Entry", description = "The newly created entry is returned. The id may not be passed.")
    public Entry add(@Valid Entry entry) throws Exception {
        return entryService.createEntry(entry);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an entry", description = "The updated entry is returned")
    public Entry update(@Valid Entry entry) {
        return entryService.updateEntry(entry);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes an entry", description = "The deleted entry is returned")
    @Path("/{id}")
    public Entry delete(@PathParam("id") long id) {
        return entryService.deleteEntry(id);
    }
}
