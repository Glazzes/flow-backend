package com.glaze.flow;

import com.glaze.flow.configuration.properties.EmailConfigurationProperties;
import com.glaze.flow.configuration.properties.KeyStoreConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@EnableConfigurationProperties({
    EmailConfigurationProperties.class,
    KeyStoreConfigurationProperties.class
})
@SpringBootApplication
public class FlowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowBackendApplication.class, args);
	}

}
