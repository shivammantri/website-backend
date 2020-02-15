package com.northeast.models.request;

import com.sun.istack.internal.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String emailId;
    @NotNull
    private String password;
}
