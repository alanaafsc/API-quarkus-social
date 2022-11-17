package io.github.alanaafsc.quarkussocial.exception;

import io.github.alanaafsc.quarkussocial.exception.NotFoundUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyApplicationExceptionHandler implements ExceptionMapper<NotFoundUserException> {

    @Override
    public Response toResponse(NotFoundUserException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }

}
