package by.ezer.mappers;

import by.ezer.dto.AuthorityCreateDTO;
import by.ezer.dto.AuthorityDTO;
import by.ezer.entity.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorityMapper {
    Authority toEntity(AuthorityCreateDTO dto);

    AuthorityDTO toDto(Authority authority);

    List<AuthorityDTO> toDto(List<Authority> authorities);
}
