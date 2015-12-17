package com.sadbeast.module;

import dagger.Module;

@Module(
        includes = {
                DatabaseModule.class,
                WebModule.class
        }
)
public class SadBeastModule {
}
