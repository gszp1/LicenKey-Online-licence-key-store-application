package org.github.gszp1.usersservice.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user"),
    ADMIN("admin");

    private final String dbValue;

    private static final Map<String, Role> DB_VALUES =
            Stream.of(values()).collect(Collectors.toMap(Role::getDbValue, role -> role));

    public static Role mapDbValueToRole(String dbValue) {
        return DB_VALUES.getOrDefault(dbValue, null);
    }
}
