package org.belajar.api.resources.user;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.dto.usersDto.UsersReqDto;
import org.belajar.api.dto.usersDto.UsersResDto;
import org.belajar.api.services.user.UsersService;

@Path("/users")
public class UsersResource {

    @Inject
    UsersService usersService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return Response.ok(usersService.getAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UsersReqDto usersReqDto){
        UsersResDto users = usersService.create(usersReqDto);
        return Response.ok(users).build();
    }


}
