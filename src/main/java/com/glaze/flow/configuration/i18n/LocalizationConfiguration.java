package com.glaze.flow.configuration.i18n;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LocalizationConfiguration {
    private static final String CLASSPATH_MESSAGES_LOCATION = "i18n/messages";

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(CLASSPATH_MESSAGES_LOCATION);
        messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.name());
        messageSource.setDefaultLocale(Locale.US);
        messageSource.setFallbackToSystemLocale(false);

        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new CustomAcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US);

        return resolver;
    }

}
