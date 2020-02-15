package com.northeast.bootstrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WebsiteConfiguration extends Configuration {
    @NotNull
    @JsonProperty(value = "secret")
    private String secret;
}
