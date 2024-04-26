package org.belajar.api.resources.event;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.dto.eventDto.event.EventReqDto;
import org.belajar.api.dto.eventDto.event.EventResDto;
import org.belajar.api.enumeration.EventEnum;
import org.belajar.api.services.event.EventService;



import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;


@Path("/event")
public class EventResource {
    @Inject
    EventService eventService;



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("page") @DefaultValue("0") int page,
                           @QueryParam("size") @DefaultValue("10") int size){
      return Response.ok(eventService.getAll(page,size)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@FormParam("dto") String dto,
                           @RestForm FileUpload file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        EventReqDto eventReqDto =mapper.readValue(dto,EventReqDto.class);
        EventResDto eventResDto = eventService.create(eventReqDto,file);
        return Response.ok(eventResDto).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response edit(@PathParam("id") Long id,@FormParam("dto") String dto,
                         @RestForm FileUpload file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        EventReqDto eventReqDto =mapper.readValue(dto,EventReqDto.class);
        EventResDto event = eventService.edit(eventReqDto,id,file);
        return Response.ok(event).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id")Long id){
        eventService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("tittle/{tittle}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByTitle(@PathParam("tittle") String tittle){
        return Response.ok(eventService.findByTitle(tittle)).build();
    }

    @GET
    @Path("category/{catId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCat(@PathParam("catId") Long catId){
        return Response.ok(eventService.findByCat(catId)).build();
    }

    @GET
    @Path("eventtheme/{etId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEventTheme(@PathParam("etId") Long etId){
        return Response.ok(eventService.findByEventTheme(etId)).build();
    }

    @GET
    @Path("needVolunteer/{needVolunteer}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByNeedVolunteer(@PathParam("needVolunteer") EventEnum.needVolunteer needVolunteer){
        return Response.ok(eventService.findByNeedVolunteer(needVolunteer)).build();
    }

    @GET
    @Path("audience/{audience}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByAudience(@PathParam("audience") EventEnum.audience audience){
        return Response.ok(eventService.findByAudience(audience)).build();
    }

    @GET
    @Path("status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByStatus(@PathParam("status") EventEnum.status status){
        return Response.ok(eventService.findByStatus(status)).build();
    }

    @GET
    @Path("startDate/{startDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByStartDate(@PathParam("startDate") String startDate){
        return Response.ok(eventService.findByStartDate(startDate)).build();
    }

    @GET
    @Path("endDate/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEndDate(@PathParam("endDate") String endDate){
        return Response.ok(eventService.findByEndDate(endDate)).build();
    }


}
