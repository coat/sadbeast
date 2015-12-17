package com.sadbeast;

import com.sadbeast.component.DaggerIntegration;
import com.sadbeast.component.DaggerSadBeast;
import com.sadbeast.component.Integration;
import com.sadbeast.service.PostServiceIT;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PostServiceIT.class})
public class IntegrationSuite {
    @BeforeClass
    public static void setUp() {
        Integration integration = DaggerIntegration.create();

        Config config = ConfigFactory.load();

        Flyway flyway = new Flyway();
        flyway.setDataSource(config.getString("db.url"), config.getString("db.username"), config.getString("db.password"));
        flyway.clean();
        flyway.migrate();
    }
}
