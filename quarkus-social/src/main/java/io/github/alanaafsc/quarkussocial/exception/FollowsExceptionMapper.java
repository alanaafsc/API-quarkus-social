package io.github.alanaafsc.quarkussocial.exception;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FollowsExceptionMapper implements ExceptionMapper<FollowsException> {

    @Override
    public Response toResponse(FollowsException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity(new ExceptionResponse(exception.getMessage())).build();
    }
}
