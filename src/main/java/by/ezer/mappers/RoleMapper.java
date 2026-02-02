package by.ezer.mappers;

import by.ezer.dto.RoleCreateDTO;
import by.ezer.dto.RoleDTO;
import by.ezer.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    Role toEntity(RoleCreateDTO dto);

    RoleDTO toDto(Role role);

    List<RoleDTO> toDto(List<Role> roles);
}
