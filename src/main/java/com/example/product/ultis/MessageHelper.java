package com.example.product.ultis;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

    private final MessageSource messageSource;

    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String key) {
        return get(key, null, key);
    }

    public String get(String key, Object[] args) {
        return get(key, args, key);
    }

    public String get(String key, Object[] args, String defaultMessage) {
        if (key == null) {
            return defaultMessage;
        }
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return defaultMessage != null ? defaultMessage : key;
        }
    }
}