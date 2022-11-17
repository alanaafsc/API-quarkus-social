package io.github.alanaafsc.quarkussocial.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UserEqualsFollowerExceptionMapper implements ExceptionMapper<UserEqualsFollowerException> {

    @Override
    public Response toResponse(UserEqualsFollowerException exception) {
        return Response.status(Response.Status.CONFLICT).entity("You can't follow yourself!").build();
    }
}
