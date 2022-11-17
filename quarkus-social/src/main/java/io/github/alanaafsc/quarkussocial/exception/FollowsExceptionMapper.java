package io.github.alanaafsc.quarkussocial.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class FollowsExceptionMapper implements ExceptionMapper<FollowsException> {

    @Override
    public Response toResponse(FollowsException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity(exception.getMessage()).build();
    }
}
