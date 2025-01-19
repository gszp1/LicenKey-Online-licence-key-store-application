package com.gszp.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gszp.backend.auth.Permission.*;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER(
            "user",
            Set.of(
                    USER_CREATE,
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE
            )
    ),
    ADMIN(
            "admin",
            Set.of(
                    USER_CREATE,
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE,
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE
            )
    );

    private final String databaseValue;

    private final Set<Permission> permissions;

    public static final Map<String, UserRole> DB_VALUES =
            Stream.of(values()).collect(Collectors.toMap(UserRole::getDatabaseValue, UserRole -> UserRole));

    public static UserRole getFromDatabaseValue(String databaseValue) {
        return DB_VALUES.get(databaseValue);
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        var grantedAuthorities = getPermissions()
                .stream()
                .map(perm -> new SimpleGrantedAuthority(perm.getName()))
                .collect(Collectors.toList());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
