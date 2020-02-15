package com.northeast.bootstrap;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.northeast.CoreModule;

public class WebsiteModule extends AbstractModule {
    private final ObjectMapper objectMapper;
    private final MetricRegistry metricRegistry;
    private final WebsiteConfiguration websiteConfiguration;

    public WebsiteModule(ObjectMapper objectMapper, MetricRegistry metricRegistry,
                         WebsiteConfiguration websiteConfiguration) {
        this.objectMapper = objectMapper;
        this.metricRegistry = metricRegistry;
        this.websiteConfiguration = websiteConfiguration;
    }

    protected void configure() {
        bind(ObjectMapper.class).toInstance(objectMapper);
        bind(MetricRegistry.class).toInstance(metricRegistry);
        bind(WebsiteConfiguration.class).toInstance(websiteConfiguration);
        install(new JpaPersistModule("sql"));
        install(new CoreModule());
    }
}
