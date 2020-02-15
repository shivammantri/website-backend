package com.northeast.models.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Slf4j
public class UserExceptionMapper implements ExceptionMapper<UserException>{

    public Response toResponse(UserException exception){
        log.error("Error while processing the request, error is : " + exception.getMessage(), exception);
        Response.Status status = exception.getStatus();
        if (status == null) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        String transactionId = MDC.get("id");
        ExceptionResponse exceptionResponse = new ExceptionResponse(transactionId,status, UserException.class.getName(), exception.getMessage());
        return Response.status(exceptionResponse.getStatus()).entity(exceptionResponse).build();
    }
}
