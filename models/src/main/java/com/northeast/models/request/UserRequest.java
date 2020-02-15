package com.northeast.models.request;

import com.northeast.models.SourceOfInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    @NotNull
    private String emailId;
    @NotNull
    private String mobile;
    private SourceOfInfo sourceOfInfo;
    @NotNull
    private String password;
}
