package com.northeast.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.northeast.models.SourceOfInfo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "User")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Long id;

    @JsonProperty
    @Column(name = "userId", unique = true)
    private String userId;

    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;

    //ToDo: Email validator
    @JsonProperty
    @Column(name = "emailId", unique = true)
    private String emailId;

    @JsonProperty
    @Column(name = "mobile", unique = true)
    private String mobile;

    @Enumerated(EnumType.STRING)
    @JsonProperty
    @Column(name = "source")
    private SourceOfInfo sourceOfInfo;

    //ToDo: Encrypt
    @JsonProperty
    @Column(name = "pass")
    private String password;

    private Boolean formEligible;
}
