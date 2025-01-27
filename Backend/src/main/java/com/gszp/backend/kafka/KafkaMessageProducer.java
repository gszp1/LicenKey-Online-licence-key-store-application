package com.gszp.backend.kafka;

import com.gszp.backend.kafka.dto.KafkaOrderMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

    @Value("${spring.kafka.topics.orders-topic}")
    private String ordersTopic;

    @Value("${spring.kafka.topics.notification-topic}")
    private String notificationTopic;

    private final KafkaTemplate<String, KafkaOrderMessage> orderTemplate;

    public void sendOrderMessage(KafkaOrderMessage orderMessage) {
        orderTemplate.send(ordersTopic, orderMessage);
    }
}
