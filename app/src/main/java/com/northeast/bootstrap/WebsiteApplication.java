package com.northeast.bootstrap;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.northeast.models.exceptions.UserExceptionMapper;
import com.northeast.resource.SampleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

import java.util.TimeZone;

@Slf4j
public class WebsiteApplication extends Application<WebsiteConfiguration> {
    private MetricRegistry metricRegistry;
    private ObjectMapper objectMapper;

    public static void main(String[] args) throws Exception {
        new WebsiteApplication().run(args);
    }

    @Override
    public void run(WebsiteConfiguration websiteConfiguration, Environment environment)  {
        final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).build();
        reporter.start();
        Injector injector = Guice.createInjector(new WebsiteModule(objectMapper,metricRegistry));
        environment.jersey().register(injector.getInstance(SampleResource.class));
        environment.jersey().register(injector.getInstance(UserExceptionMapper.class));
        injector.getInstance(PersistService.class).start();
        log.info("Website Application is up!");
    }

    @Override
    public void initialize(Bootstrap<WebsiteConfiguration> bootstrap) {
        metricRegistry = bootstrap.getMetricRegistry();
        objectMapper = bootstrap.getObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        SimpleModule simpleModule = new SimpleModule();
        objectMapper.registerModule(simpleModule);
        objectMapper.setTimeZone(TimeZone.getDefault());
    }
}
