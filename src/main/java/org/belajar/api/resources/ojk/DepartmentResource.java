package org.belajar.api.resources.ojk;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Id;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.belajar.api.dto.ojkDto.department.DepartmentReqDto;
import org.belajar.api.services.ojk.DepartmentService;

@Path("/department")
public class DepartmentResource {
    @Inject
    DepartmentService departmentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        return Response.ok(departmentService.getAll()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(DepartmentReqDto departmentReqDto){
        return Response.ok(departmentService.create(departmentReqDto)).build();
    }
}
