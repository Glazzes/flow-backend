package com.glaze.flow;

import com.glaze.flow.configuration.population.Oauth2RegisteredClientPopulationConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
public class FlowBackendApplication implements InitializingBean {
    private final Oauth2RegisteredClientPopulationConfiguration registeredClientPopulationConfiguration;

    @Autowired
    public FlowBackendApplication(
        Oauth2RegisteredClientPopulationConfiguration registeredClientPopulationConfiguration
    ) {
        this.registeredClientPopulationConfiguration = registeredClientPopulationConfiguration;
    }

	public static void main(String[] args) {
		SpringApplication.run(FlowBackendApplication.class, args);
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        // registeredClientPopulationConfiguration.populate();
    }
}
