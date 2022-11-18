package io.github.alanaafsc.quarkussocial.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    public final static int UNPROCESSABLE_ENTITY_STATUS = 422;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(UNPROCESSABLE_ENTITY_STATUS).entity(ConstraintViolationResponse.of(exception)).build();
    }

}
