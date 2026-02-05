package by.ezer.service;

import by.ezer.dto.ProductCreateDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.dto.RoleCreateDTO;
import by.ezer.dto.RoleDTO;
import by.ezer.entity.Product;
import by.ezer.entity.Role;
import by.ezer.exceptions.ServiceException;
import by.ezer.mappers.RoleMapper;
import by.ezer.repositories.api.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Transactional
    public RoleDTO createRole(RoleCreateDTO request) {
        try {
            Role role = new Role(request.name());

            roleRepository.save(role);

            return roleMapper.toDto(role);
        } catch (Exception e) {
            throw new ServiceException("Cannot save role in service", HttpStatus.BAD_REQUEST);
        }
    }
}
