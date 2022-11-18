package io.github.alanaafsc.quarkussocial.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoFollowerExceptionMapper implements ExceptionMapper<NoFollowerException> {

    @Override
    public Response toResponse(NoFollowerException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }

}
