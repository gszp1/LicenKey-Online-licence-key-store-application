package com.gszp.keyfunction;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class KeyFunction {

    @Bean
    @Transactional
    public Consumer<Message<String>> handleKeyEvent() {
        return event -> {

        };
    }
}
