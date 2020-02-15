package com.northeast.bootstrap;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.northeast.CoreModule;

public class WebsiteModule extends AbstractModule {
    private final ObjectMapper objectMapper;
    private final MetricRegistry metricRegistry;

    public WebsiteModule(ObjectMapper objectMapper, MetricRegistry metricRegistry) {
        this.objectMapper = objectMapper;
        this.metricRegistry = metricRegistry;
    }

    protected void configure() {
        bind(ObjectMapper.class).toInstance(objectMapper);
        bind(MetricRegistry.class).toInstance(metricRegistry);
        install(new JpaPersistModule("sql"));
        install(new CoreModule());
    }
}
