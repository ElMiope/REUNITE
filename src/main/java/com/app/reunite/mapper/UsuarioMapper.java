package com.app.reunite.mapper;

import com.app.reunite.entities.DTOs.RegisterRequest;
import com.app.reunite.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UsuarioMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "usuario_id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "rol", expression = "java(com.app.reunite.enums.Rol.USUARIO)")
    public abstract Usuario toUsuario(RegisterRequest request);

    @Named("encodePassword")
    protected String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
