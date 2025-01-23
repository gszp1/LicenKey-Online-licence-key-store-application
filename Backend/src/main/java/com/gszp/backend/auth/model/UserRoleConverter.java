package com.gszp.backend.auth.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

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
