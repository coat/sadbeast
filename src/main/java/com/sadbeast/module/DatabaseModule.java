package com.sadbeast.module;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dagger.Module;
import dagger.Provides;
import org.dalesbred.Database;

import javax.inject.Singleton;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    public Database provideDatabase() {
        Config config = ConfigFactory.load();

        HikariConfig hikari = new HikariConfig();
        hikari.setMaximumPoolSize(config.getInt("db.poolSize"));
        hikari.setJdbcUrl(config.getString("db.url"));
        hikari.setUsername(config.getString("db.username"));
        hikari.setPassword(config.getString("db.password"));

        return Database.forDataSource(new HikariDataSource(hikari));
    }
}
