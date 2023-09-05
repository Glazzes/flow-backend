package com.glaze.flow.configuration.i18n;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class LocalizationConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new CustomAcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US);

        return resolver;
    }

}
