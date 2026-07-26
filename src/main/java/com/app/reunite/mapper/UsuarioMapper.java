package com.app.reunite.mapper;

import com.app.reunite.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.userdetails.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UsuarioMapper {
    @Mapping(target = "authorities",ignore = true)
    Usuario mapToUsuario(User user);
    User mapToUser(Usuario usuario);
}
