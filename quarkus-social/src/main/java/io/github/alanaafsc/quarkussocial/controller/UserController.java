package io.github.alanaafsc.quarkussocial.controller;

import io.github.alanaafsc.quarkussocial.dto.CreateUserRequest;
import io.github.alanaafsc.quarkussocial.dto.ResponseError;
import io.github.alanaafsc.quarkussocial.model.User;
import io.github.alanaafsc.quarkussocial.repository.UserRepository;
import io.github.alanaafsc.quarkussocial.service.UserService;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @POST
    public Response createUser(CreateUserRequest userRequest){

        User user = userService.saveUser(userRequest);
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }

    @GET
    public Response listAllUsers(){
        List<User> users = userService.findAllUsers();
        return Response.ok(users).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") Long id){
        userService.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData){
        userService.update(id, userData);
        return Response.noContent().build();

    }
}
