package com.atherys.rpg.repository.converter;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.character.Role;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getName();
    }

    @Override
    public Role convertToEntityAttribute(String roleId) {
        return AtherysRPG.getInstance().getCharacterService().getRole(roleId).orElse(Role.citizen);
    }
}
