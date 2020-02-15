package com.northeast.models.exceptions;

import lombok.Data;

import javax.ws.rs.core.Response;

@Data
public class UserException extends RuntimeException{
    private Response.Status status;
    private String message;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
        this.message=message;
        status=Response.Status.INTERNAL_SERVER_ERROR;
    }

    public UserException(String message, Response.Status status) {
        super(message);
        this.message=message;
        this.status=status;
    }

    public UserException(String message, Throwable cause) {
        super(message,cause);
        this.message=message;
        this.status=Response.Status.INTERNAL_SERVER_ERROR;
    }

    protected UserException(String message, Response.Status status, Throwable cause) {
        super(message, cause);
        this.message=message;
        this.status=status;
    }

}
