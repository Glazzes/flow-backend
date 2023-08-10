package com.glaze.flow.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAcceptHeaderLocaleResolver.class);
    private static final Pattern PATTERN = Pattern.compile("^([a-z]{1,8})(?:[_-]([A-Z]{1,8}))?.*$");

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String localeHeader = request.getHeader("Accept-Language");
        if(localeHeader != null) {
            Locale currentLocale = convertAcceptHeeaderToLocale(localeHeader);
            if (currentLocale != null) {
                return currentLocale;
            }
        }

        Locale requestLocale = request.getLocale();
        List<Locale> supportedLocales = getSupportedLocales();
        if (supportedLocales.isEmpty() || supportedLocales.contains(requestLocale)) {
            return requestLocale;
        }

        Locale defaultLocale = getDefaultLocale();
        return (defaultLocale != null ? defaultLocale : requestLocale);
    }

    public Locale convertAcceptHeeaderToLocale(String acceptLanguageHeader) {
        LOGGER.info("Parsing Accept-Language header {} to Locale", acceptLanguageHeader);
        Matcher matcher = PATTERN.matcher(acceptLanguageHeader);
        boolean doesNotMatchRegex = !matcher.matches();
        if (doesNotMatchRegex) {
            return null;
        }

        String language = matcher.group(1);
        String region = matcher.group(2);
        Locale.Builder builder = new Locale.Builder();
        try {
            if (language != null )builder.setLanguage(language);
            if (region != null )builder.setRegion(region);
            LOGGER.info("Accept-Language {} header was successfully parsed", acceptLanguageHeader);

            return builder.build();
        }catch (IllformedLocaleException e) {
            e.printStackTrace();
        }

        return null;
    }

}
