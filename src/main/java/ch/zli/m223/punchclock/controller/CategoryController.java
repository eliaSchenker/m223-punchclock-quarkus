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

import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.service.CategoryService;

@Path("/categories")
@Tag(name = "Categories", description = "Handling of categories")
public class CategoryController {
    @Inject
    CategoryService categoryService;

    /**
     * Returns a list of all categories
     * Only available to logged in users
     * @return list of categories
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"User", "Admin"})
    @Operation(summary = "List all categories", description = "")
    public List<Category> list() {
        return categoryService.findAll();
    }

    /**
     * Adds a category
     * Only available to administrators
     * @param category The to be added category
     * @return Returns the new category
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Operation(summary = "Add a new Category", description = "The newly created category is returned. The id may not be passed.")
    public Category add(@Valid Category category) {
        return categoryService.createCategory(category);
    }

    /**
     * Updates a category
     * Only available to administrators
     * @param category The category containing new information 
     * @return The deleted category
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Operation(summary = "Updates a category", description = "The updated category is returned")
    public Category update(@Valid Category category) {
        return categoryService.updateCategory(category);
    }

    /**
     * Deletes a category
     * Only available to administrators
     * @param id The id of the category
     * @return The deleted category
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    @Operation(summary = "Deletes a category", description = "The deleted category is returned")
    @Path("/{id}")
    public Category delete(@PathParam("id") long id) {
        return categoryService.deleteCategory(id);
    }
}
