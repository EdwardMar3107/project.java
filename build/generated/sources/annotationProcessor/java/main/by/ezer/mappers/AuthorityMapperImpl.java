package by.ezer.mappers;

import by.ezer.dto.AuthorityCreateDTO;
import by.ezer.dto.AuthorityDTO;
import by.ezer.entity.Authority;
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
public class AuthorityMapperImpl implements AuthorityMapper {

    @Override
    public Authority toEntity(AuthorityCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Authority.AuthorityBuilder authority = Authority.builder();

        authority.name( dto.name() );

        return authority.build();
    }

    @Override
    public AuthorityDTO toDto(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = authority.getId();
        name = authority.getName();

        AuthorityDTO authorityDTO = new AuthorityDTO( id, name );

        return authorityDTO;
    }

    @Override
    public List<AuthorityDTO> toDto(List<Authority> authorities) {
        if ( authorities == null ) {
            return null;
        }

        List<AuthorityDTO> list = new ArrayList<AuthorityDTO>( authorities.size() );
        for ( Authority authority : authorities ) {
            list.add( toDto( authority ) );
        }

        return list;
    }
}
