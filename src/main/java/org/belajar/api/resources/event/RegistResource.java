package org.belajar.api.resources.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.dto.eventDto.regist.RegistReqDto;
import org.belajar.api.services.event.RegistService;

@Path("/registEvent")
public class RegistResource {

    @Inject
    RegistService registService;

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registEvent(@PathParam("id") Long id, RegistReqDto registEventDto){
        return Response.ok(registService.registEvent(id, registEventDto)).build();
    }

    @DELETE
    @Path("/{eventId}/users/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRegistration(@PathParam("eventId") Long eventId, @PathParam("userId") Long userId) {
        registService.deleteRegist(userId, eventId);
        return Response.ok().build();
    }



}
