package org.belajar.api.resources.event;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.services.event.HistoryService;

@Path("/history")
public class HistoryResource {
    @Inject
    HistoryService historyService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return Response.ok(historyService.getAll()).build();
    }
}
