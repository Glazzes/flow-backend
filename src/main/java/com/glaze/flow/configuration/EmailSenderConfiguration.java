package com.glaze.flow.configuration;

import java.util.Properties;

import com.glaze.flow.configuration.properties.EmailConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailSenderConfiguration {
    private static final String MAIL_AUTHENTICATION_PROPERTY = "mail.smtp.auth";
    private static final String MAIL_ENABLE_TLS_PROPERTY = "mail.smtp.starttls.enable";

    @Bean
    public JavaMailSender javaMailSender(EmailConfigurationProperties emailConfigurationProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfigurationProperties.host());
        mailSender.setPort(emailConfigurationProperties.port());
        mailSender.setProtocol(emailConfigurationProperties.protocol());
        mailSender.setUsername(emailConfigurationProperties.username());
        mailSender.setPassword(emailConfigurationProperties.password());

        Properties properties = new Properties();
        properties.setProperty(MAIL_AUTHENTICATION_PROPERTY, Boolean.TRUE.toString());
        properties.setProperty(MAIL_ENABLE_TLS_PROPERTY, Boolean.TRUE.toString());

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

}
