package com.sadbeast.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sadbeast.SadBeastApplication;
import dagger.Module;
import dagger.Provides;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Module
public class WebModule {
    @Singleton
    @Provides
    public Configuration provideConfiguration() {
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        config.setClassForTemplateLoading(SadBeastApplication.class, "templates");

        return config;
    }

    @Singleton
    @Provides
    public Validator provideValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        return validatorFactory.getValidator();
    }

    @Singleton
    @Provides
    public ObjectMapper provideObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper;
    }
}
