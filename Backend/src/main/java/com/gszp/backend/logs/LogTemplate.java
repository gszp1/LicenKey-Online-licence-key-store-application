package com.gszp.backend.logs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogTemplate {
    RECEIVED_REQUEST("Received request"),
    REQUEST_SUCCESS("Request handling successful"),
    REQUEST_FAIL("Request handling failed"),
    REQUEST_PROCESSING("Processing request");

    private final String message;
}
