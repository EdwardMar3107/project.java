package by.ezer.mappers;

import by.ezer.dto.UserDTO;
import by.ezer.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-10T22:17:37+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.0.0.jar, environment: Java 25.0.1 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.name( user.getUserName() );
        userDTO.id( user.getId() );
        userDTO.age( user.getAge() );
        userDTO.email( user.getEmail() );

        return userDTO.build();
    }
}
