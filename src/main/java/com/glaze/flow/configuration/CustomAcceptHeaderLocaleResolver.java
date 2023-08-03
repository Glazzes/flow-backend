package com.glaze.flow.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

public class CustomAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String localeHeader = request.getHeader("Accept-Language");
        if(localeHeader != null) {
            String languageCode = this.extractLanguageCode(localeHeader);
            return new Locale(languageCode);
        }

        Locale requestLocale = request.getLocale();
        List<Locale> supportedLocales = getSupportedLocales();
        if (supportedLocales.isEmpty() || supportedLocales.contains(requestLocale)) {
            return requestLocale;
        }

        Locale defaultLocale = getDefaultLocale();
        return (defaultLocale != null ? defaultLocale : requestLocale);
    }

    public String extractLanguageCode(String acceptLanguageHeader) {
        StringBuilder builder = new StringBuilder();
        for(char c : acceptLanguageHeader.toCharArray()) {
            if(c == '_') break;
            builder.append(c);
        }

        return builder.toString();
    }

}
