package com.gszp.backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String databaseValue;

    public static final Map<String, UserRole> DB_VALUES =
            Stream.of(values()).collect(Collectors.toMap(UserRole::getDatabaseValue, UserRole -> UserRole));

    public static UserRole getFromDatabaseValue(String databaseValue) {
        return DB_VALUES.get(databaseValue);
    }
}
