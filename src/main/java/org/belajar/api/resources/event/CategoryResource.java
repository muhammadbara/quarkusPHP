package org.belajar.api.resources.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.dto.eventDto.category.CategoryReqDto;
import org.belajar.api.dto.eventDto.category.CategoryResDto;
import org.belajar.api.services.event.CategoryService;

@Path("/category")
public class CategoryResource{

    @Inject
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return Response.ok(categoryService.getAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CategoryReqDto categoryReqDto){
        CategoryResDto category = categoryService.create(categoryReqDto);
        return Response.ok(category).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(CategoryReqDto categoryReqDto, @PathParam("id") Long id){
        CategoryResDto category = categoryService.edit(id, categoryReqDto);
        return Response.ok(category).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id){
        categoryService.delete(id);
        return Response.noContent().build();
    }
}
