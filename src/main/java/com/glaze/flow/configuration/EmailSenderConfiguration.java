package com.glaze.flow.configuration;

import java.util.Properties;

import com.glaze.flow.configuration.properties.EmailConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailSenderConfiguration {

    @Bean
    public JavaMailSender javaMailSender(EmailConfigurationProperties emailConfigurationProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfigurationProperties.host());
        mailSender.setPort(emailConfigurationProperties.port());
        mailSender.setProtocol(emailConfigurationProperties.protocol());
        mailSender.setUsername(emailConfigurationProperties.username());
        mailSender.setPassword(emailConfigurationProperties.password());

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }

}
