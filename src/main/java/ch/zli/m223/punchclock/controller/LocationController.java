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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.punchclock.domain.Location;
import ch.zli.m223.punchclock.service.LocationService;

@Path("/locations")
@Tag(name = "Locations", description = "Handling of a users location")
public class LocationController {
    @Inject
    LocationService locationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Operation(summary = "List all locations", description = "")
    public List<Location> list() {
        return locationService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Operation(summary = "Add a new location", description = "The newly created location is returned. The id may not be passed.")
    public Location add(@Valid Location location) {
        return locationService.createLocation(location);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Operation(summary = "Updates a location", description = "The updated location is returned")
    public Location update(@Valid Location location) {
        return locationService.updateLocation(location);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Operation(summary = "Deletes a Location", description = "The deleted location is returned")
    @Path("/{id}")
    public Location delete(@PathParam("id") long id) {
        return locationService.deleteLocation(id);
    }
}
