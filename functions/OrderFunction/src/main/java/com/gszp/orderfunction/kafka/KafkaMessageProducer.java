package com.gszp.orderfunction.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

    @Value("${spring.kafka.topics.keys-topic}")
    private String keysTopic;

    private final KafkaTemplate<String, KeyEventDto> orderTemplate;

    public void sendKeyMessage(KeyEventDto keyEventDto) {
        orderTemplate.send(keysTopic, keyEventDto);
    }
}
