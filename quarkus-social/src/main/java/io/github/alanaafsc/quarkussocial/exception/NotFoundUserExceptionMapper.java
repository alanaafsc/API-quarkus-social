package io.github.alanaafsc.quarkussocial.exception;

import io.github.alanaafsc.quarkussocial.exception.NotFoundUserException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Provider
public class NotFoundUserExceptionMapper implements ExceptionMapper<NotFoundUserException> {

    @Override
    public Response toResponse(NotFoundUserException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ExceptionResponse("User Not Found")).build();
    }

}
