package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.observation.graphql.GraphQlObservationAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = GraphQlObservationAutoConfiguration.class)
@EntityScan(basePackages = "infrastructure.persistence.entities")
@EnableJpaRepositories(basePackages = "infrastructure.persistence.repositories")
@ComponentScan(basePackages = {"application", "infrastructure.persistence", "infrastructure.persistence.mapper", "config"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
