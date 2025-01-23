package com.gszp.backend.logs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogGenerator {

    public static void generateInfoLog(LogTemplate template, String message) {
        log.info("{} - {}", template.getMessage(), message);
    }
}