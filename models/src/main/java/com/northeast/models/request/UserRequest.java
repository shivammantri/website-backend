package com.northeast.models.request;

import com.northeast.models.SourceOfInfo;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobile;
    private SourceOfInfo sourceOfInfo;
    private String password;
}
