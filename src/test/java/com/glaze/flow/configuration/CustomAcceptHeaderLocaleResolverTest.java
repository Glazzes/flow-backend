package com.glaze.flow.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CustomHeaderLocaleResolver Tests")
class CustomAcceptHeaderLocaleResolverTest {

    private final CustomAcceptHeaderLocaleResolver underTest = new CustomAcceptHeaderLocaleResolver();

    @Test
    void itShouldParseAcceptLanguageHeader() {
        // Given
        String acceptLanguageHeader = "es_ES";

        // Then
        Locale expectedLocale = new Locale("es", "ES");
        Locale current = underTest.convertAcceptHeeaderToLocale(acceptLanguageHeader);
        assertThat(current).isEqualTo(expectedLocale);
    }

    @Test
    void itShouldPartiallyParseAcceptLanguageHeader() {
        // Given
        String acceptLanguageHeader = "en_U";

        // Then
        Locale expectedLocale = new Locale("en");
        Locale current = underTest.convertAcceptHeeaderToLocale(acceptLanguageHeader);
        assertThat(current).isEqualTo(expectedLocale);
    }

    @Test
    void itShouldNotParseAcceptLanguageHeader() {
        // Given
        String acceptLanguageHeader = "r_R";

        // Then
        Locale parsedLocale = underTest.convertAcceptHeeaderToLocale(acceptLanguageHeader);
        assertThat(parsedLocale).isNull();
    }

}
