package com.gszp.backend.auth;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        }
        return userStatus.getDatabaseValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(String databaseValue) {
        return UserStatus.getByDatabaseValue(databaseValue);
    }
}
