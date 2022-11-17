package io.github.alanaafsc.quarkussocial.exception;

import io.github.alanaafsc.quarkussocial.exception.NotFoundUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundUserExceptionMapper implements ExceptionMapper<NotFoundUserException> {
    public static final String MESSAGE_USER_NOT_FOUND = "User Not Found";

    @Override
    public Response toResponse(NotFoundUserException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(MESSAGE_USER_NOT_FOUND).build();
    }

}
