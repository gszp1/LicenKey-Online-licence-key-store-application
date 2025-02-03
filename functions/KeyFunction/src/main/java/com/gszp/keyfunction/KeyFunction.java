package com.gszp.keyfunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gszp.keyfunction.dto.KeyDto;
import com.gszp.keyfunction.dto.KeyEventDto;
import com.gszp.keyfunction.model.Key;
import com.gszp.keyfunction.repository.KeyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.function.Consumer;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyFunction {

    private final KeyRepository keyRepository;
    private final ObjectMapper objectMapper;

    @Bean
    @Transactional
    public Consumer<Message<String>> handleKeyEvent() {
        return event -> {
            log.info("KeyEvent received: {}", event.getPayload());
            Optional<KeyEventDto> keyEventDtoOp = readEventData(event);
            if (keyEventDtoOp.isEmpty()) {
                log.info("Failed to read event data");
                return;
            }
            KeyEventDto keyEventDto = keyEventDtoOp.get();
            log.info("Processing key event for key with id: {}", keyEventDto.getKeyId());

            Optional<Key> keyOp = keyRepository.findById(keyEventDto.getKeyId());
            if (keyOp.isEmpty()) {
                log.info("Key with given key was not found.");
                return;
            }
            Key key = keyOp.get();
            log.info("Retrieved key from database.");

            log.info(
                    "Attempting to retrieve data from key service with address: {}",
                    key.getLicence().getService().getApiUrl()
            );
            RestTemplate restTemplate = new RestTemplate();
            KeyDto response = restTemplate.getForObject(
                    key.getLicence().getService().getApiUrl(),
                    KeyDto.class
            );
            if (response == null) {
                log.info("Failed to retrieve data from key service.");
                return;

            }
            if (response.getKey() == null) {
                log.info("Received response from service but key was not found.");
                return;
            }

            log.info("Retrieved key from service.");
            key.setKeyCode(response.getKey());
            keyRepository.save(key);
            log.info("Saved key code for key with id: {}", key.getKeyId());
        };
    }

    private Optional<KeyEventDto> readEventData(Message<String> message) {
        String payload = message.getPayload();
        if (payload.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(payload, KeyEventDto.class));
        } catch (Exception e) {
            log.error("Error reading event data: ", e);
            return Optional.empty();
        }
    }
}
