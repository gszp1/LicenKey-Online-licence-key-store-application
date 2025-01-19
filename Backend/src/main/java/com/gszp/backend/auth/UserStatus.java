package com.gszp.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum UserStatus {
    ACTIVE("active"),
    DEACTIVATED("deactivated"),
    BANNED("banned");

    private final String databaseValue;

    private static final Map<String, UserStatus> DB_VALUES =
        Stream.of(values()).collect(Collectors.toMap(UserStatus::getDatabaseValue, UserStatus -> UserStatus));

    public static UserStatus getByDatabaseValue(String databaseValue) {
        return DB_VALUES.get(databaseValue);
    }
}
