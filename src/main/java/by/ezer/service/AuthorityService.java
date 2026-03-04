package by.ezer.service;

import by.ezer.dto.*;
import by.ezer.entity.Authority;
import by.ezer.entity.Product;
import by.ezer.entity.User;
import by.ezer.exceptions.ServiceException;
import by.ezer.mappers.AuthorityMapper;
import by.ezer.repositories.api.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    @Transactional
    public AuthorityDTO createAuthority(AuthorityCreateDTO request) {
        try {
            Authority authority = new Authority(request.name());

            authorityRepository.save(authority);

            return authorityMapper.toDto(authority);
        } catch (Exception e) {
            throw new ServiceException("Cannot save authority in service", HttpStatus.BAD_REQUEST);
        }
    }
}
