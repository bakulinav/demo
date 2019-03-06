package dev.abakulin.demo;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public abstract class AbstractIntegrationTest {

    @ClassRule
    public static GenericContainer postgres = new PostgreSQLContainer()
            .withDatabaseName("demo")
            .withUsername("abakulin")
            .withPassword("secret")
            .withExposedPorts(5432);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    String.format("spring.datasource.url=jdbc:postgresql://%s:%d/demo",
                            postgres.getContainerIpAddress(), postgres.getMappedPort(5432))
            );
            values.applyTo(configurableApplicationContext);
        }
    }

}
