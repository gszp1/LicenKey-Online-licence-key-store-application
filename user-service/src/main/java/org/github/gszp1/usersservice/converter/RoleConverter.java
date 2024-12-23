package org.github.gszp1.usersservice.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.github.gszp1.usersservice.auth.Role;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        if (role == null) {
            return null;
        }
        return role.getDbValue();
    }

    @Override
    public Role convertToEntityAttribute(String dbValue) {
        return Role.mapDbValueToRole(dbValue);
    }
}
