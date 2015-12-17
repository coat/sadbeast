package com.sadbeast.component;

import com.sadbeast.module.SadBeastModule;
import com.sadbeast.service.PostServiceIT;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(
        modules = SadBeastModule.class
)
public interface Integration {
}
