package com.mriganka.microservices.config_server;

import com.mriganka.microservices.config_server.service.EnvironmentRepositoryConfiguration;
import com.mriganka.microservices.config_server.service.MongoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
/**
 * Created by Mriganka Shekhar Roy on 12/13/2018.
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ConfigServerApplicationTest.MongoConfigServerApplication.class,
        webEnvironment = RANDOM_PORT
)
@TestPropertySource(locations = "classpath:application_test.properties")*/
public class ConfigServerApplicationTest {


    /*@LocalServerPort
    private int port;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void server() {
     Environment environment = new TestRestTemplate().getForObject("http://localhost:"
                + port + "/workflow-engine/default", Environment.class);
        assertEquals("mongodb", environment.getPropertySources().get(0).getName());
    }


    @SpringBootApplication
    @EnableConfigServer
    @Import({
            EnvironmentRepositoryConfiguration.class,
            MongoConfiguration.class})
    public static class MongoConfigServerApplication {

        public static void main(String args[]) {
            SpringApplication.run(MongoConfigServerApplication.class, args);
        }

    }*/
}
