package com.gszp.backend.logs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogGenerator {

    public static String generateInfoLog(LogTemplate template, String message) {
        return String.format("%s - %s", template.getMessage(), message);
    }
}