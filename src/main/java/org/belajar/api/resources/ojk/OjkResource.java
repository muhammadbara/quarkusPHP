package org.belajar.api.resources.ojk;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.dto.eventDto.event.EventReqDto;
import org.belajar.api.dto.eventDto.event.EventResDto;
import org.belajar.api.dto.ojkDto.ojk.OjkReqDto;
import org.belajar.api.dto.ojkDto.ojk.OjkResDto;
import org.belajar.api.entity.ojk.Ojk;
import org.belajar.api.enumeration.OjkEnum;
import org.belajar.api.services.ojk.OjkService;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;

@Path("/ojk")
public class OjkResource {
    @Inject
    OjkService ojkService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return Response.ok(ojkService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(Long id){

        return Response.ok(ojkService.findById(id)).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@FormParam("dto") String dto,
                           @RestForm FileUpload file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OjkReqDto ojkReqDto =mapper.readValue(dto,OjkReqDto.class);
        OjkResDto ojkResDto = ojkService.create(ojkReqDto,file);
        return Response.ok(ojkResDto).build();
    }

    @PUT
    @Path("/{ojkId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response edit(@PathParam("ojkId") Long id,@FormParam("dto") String dto,
                         @RestForm FileUpload file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        OjkReqDto ojkReqDto =mapper.readValue(dto,OjkReqDto.class);
        OjkResDto ojk = ojkService.edit(id,ojkReqDto,file);
        return Response.ok(ojk).build();
    }

    @DELETE
    @Path("/{ojkId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteById(@PathParam("ojkId") Long ojkId){
        ojkService.deleteById(ojkId);
        return Response.noContent().build();
    }

    @GET
    @Path("/title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByTitle(@PathParam("title") String title){
        return Response.ok(ojkService.findByTitle(title)).build();
    }

    @GET
    @Path("/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByStatus(@PathParam("status") OjkEnum.status status){
        return Response.ok(ojkService.findByStatus(status)).build();
    }

    @GET
    @Path("/attachment/{attachment}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByAttachment(@PathParam("attachment") String attachment){
        return Response.ok(ojkService.findByAttachment(attachment)).build();
    }

    @GET
    @Path("/startDate/{startDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByStartDate(@PathParam("startDate") String startDate){
        return Response.ok(ojkService.findByStartDate(startDate)).build();
    }

    @GET
    @Path("/endDate/{endDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByEndDate(@PathParam("endDate") String endDate){
        return Response.ok(ojkService.findByEndDate(endDate)).build();
    }

    @GET
    @Path("/reminderType/{reminderType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findReminderType(@PathParam("reminderType") OjkEnum.reminderType reminderType){
        return Response.ok(ojkService.findByReminderType(reminderType)).build();
    }

    @GET
    @Path("/priority/{priority}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPriority(@PathParam("priority") OjkEnum.priority priority){
        return Response.ok(ojkService.findByPriority(priority)).build();
    }






}
