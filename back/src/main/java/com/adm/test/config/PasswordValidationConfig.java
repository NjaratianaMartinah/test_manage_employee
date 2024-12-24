package com.adm.test.config;

import lombok.Setter;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Setter
@Configuration
@ConfigurationProperties(prefix = "password")
public class PasswordValidationConfig {
    private int minLength;
    private int maxLength;
    private int minUppercase;
    private int minDigits;
    private int minSpecial;

    @Bean
    public PasswordValidator passwordValidator() {
        return new PasswordValidator(
                Arrays.asList(
                        new LengthRule(minLength, maxLength),
                        new CharacterRule(EnglishCharacterData.UpperCase, minUppercase),
                        new CharacterRule(EnglishCharacterData.Digit, minDigits),
                        new CharacterRule(EnglishCharacterData.Special, minSpecial)
                )
        );

    }

}

