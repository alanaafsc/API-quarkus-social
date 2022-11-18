package io.github.alanaafsc.quarkussocial.exception;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static io.github.alanaafsc.quarkussocial.dto.ResponseError.UNPROCESSABLE_ENTITY_STATUS;


@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(UNPROCESSABLE_ENTITY_STATUS).entity(ConstraintViolationResponse.of(exception)).build();
    }

}
