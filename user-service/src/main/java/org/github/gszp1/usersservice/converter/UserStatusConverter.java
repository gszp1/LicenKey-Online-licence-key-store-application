package org.github.gszp1.usersservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.github.gszp1.usersservice.auth.UserStatus;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        }
        return userStatus.getDbValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbValue) {
        return UserStatus.valueOf(dbValue);
    }
}
