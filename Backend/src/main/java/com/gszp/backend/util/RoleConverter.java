package com.gszp.backend.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        return userRole.getDatabaseValue();
    }

    @Override
    public UserRole convertToEntityAttribute(String databaseValue) {
        return UserRole.getFromDatabaseValue(databaseValue);
    }
}
