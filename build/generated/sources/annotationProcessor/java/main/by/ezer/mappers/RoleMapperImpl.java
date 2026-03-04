package by.ezer.mappers;

import by.ezer.dto.RoleCreateDTO;
import by.ezer.dto.RoleDTO;
import by.ezer.entity.Role;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-04T19:14:51+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.name( dto.name() );

        return role.build();
    }

    @Override
    public RoleDTO toDto(Role role) {
        if ( role == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = role.getId();
        name = role.getName();

        RoleDTO roleDTO = new RoleDTO( id, name );

        return roleDTO;
    }

    @Override
    public List<RoleDTO> toDto(List<Role> roles) {
        if ( roles == null ) {
            return null;
        }

        List<RoleDTO> list = new ArrayList<RoleDTO>( roles.size() );
        for ( Role role : roles ) {
            list.add( toDto( role ) );
        }

        return list;
    }
}
