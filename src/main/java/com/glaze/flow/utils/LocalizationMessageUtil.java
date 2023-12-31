package com.glaze.flow.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LocalizationMessageUtil {

    private final MessageSource messageSource;

    public LocalizationMessageUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String messageKey, Object... parameters) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, parameters, locale);
    }

}
