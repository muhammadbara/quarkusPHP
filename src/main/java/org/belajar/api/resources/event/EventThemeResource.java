package org.belajar.api.resources.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.dto.eventDto.eventTheme.EventThemeReqDto;
import org.belajar.api.dto.eventDto.eventTheme.EventThemeResDto;
import org.belajar.api.services.event.EventThemeService;

import java.util.List;

@Path("/eventtheme")
public class EventThemeResource {
    @Inject
    EventThemeService eventThemeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<EventThemeResDto> eventTheme = eventThemeService.getAll();
        return Response.ok(eventTheme).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(EventThemeReqDto eventThemeReqDto){
        EventThemeResDto eventTheme = eventThemeService.create(eventThemeReqDto);
        return Response.ok(eventTheme).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") Long id, EventThemeReqDto eventThemeReqDto){
        EventThemeResDto eventTheme = eventThemeService.edit(eventThemeReqDto, id);
        return Response.ok(eventTheme).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id){
        eventThemeService.delete(id);
        return Response.noContent().build();

    }
}
