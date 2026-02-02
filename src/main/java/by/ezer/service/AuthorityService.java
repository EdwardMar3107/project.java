package by.ezer.service;

import by.ezer.dto.AuthorityCreateDTO;
import by.ezer.dto.AuthorityDTO;
import by.ezer.entity.Authority;
import by.ezer.mappers.AuthorityMapper;
import by.ezer.repositories.api.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    public AuthorityDTO findById(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find authority by id in service", HttpStatus.BAD_REQUEST));
        return authorityMapper.toDto(authority);
    }

    public List<AuthorityDTO> findAll() {
        try {
            return authorityMapper.toDto(authorityRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Cannot find all authorities in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public AuthorityDTO save(AuthorityCreateDTO authorityCreationDto) {
        try {
            Authority entity = authorityMapper.toEntity(authorityCreationDto);
            return authorityMapper.toDto(authorityRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save this authority in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public AuthorityDTO update(Long id, AuthorityCreateDTO authorityCreationDto) {
        try {
            Authority authority = authorityRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cannot find authority by id in service", HttpStatus.BAD_REQUEST));
            Authority entity = authorityMapper.toEntity(authorityCreationDto);
            updateAuthority(authority, entity);
            authorityRepository.save(authority);
            return authorityMapper.toDto(authority);
        } catch (Exception e) {
            throw new ServiceException("Cannot update this authority in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            authorityRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this authority in service", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateAuthority(Authority authority, Authority source){
        authority.setName(source.getName());
        authority.setRoles(source.getRoles());
    }
}
