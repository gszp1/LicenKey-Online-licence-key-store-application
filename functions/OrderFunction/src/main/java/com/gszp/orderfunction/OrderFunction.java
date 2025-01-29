package com.gszp.orderfunction;

import io.cloudevents.CloudEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrderFunction {

    @Bean
    public Consumer<CloudEvent> handleOrderEvent() {
        return event -> {

        };
    }

}

