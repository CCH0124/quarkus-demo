package org.cch.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(final Exception exception) {
        if (exception instanceof AuthenticationPasswordException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Wrong password")
                    .build();
        }
        if (exception instanceof AuthenticationAccountException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Wrong Account")
                    .build();
        }

        if (exception instanceof AuthenticationRoleException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Wrong Role")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(exception.getMessage())
                .build();
    }
}
