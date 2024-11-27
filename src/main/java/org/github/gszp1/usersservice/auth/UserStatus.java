package org.github.gszp1.usersservice.auth;


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

    private final String dbValue;

    private static final Map<String, UserStatus> DB_VALUES =
            Stream.of(values()).collect(Collectors.toMap(UserStatus::getDbValue, UserStatus -> UserStatus));

    public static UserStatus mapDbValueToStatus(String dbValue) {
        return DB_VALUES.get(dbValue);
    }

}
