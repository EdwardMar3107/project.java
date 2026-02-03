package by.ezer.service;

import by.ezer.dto.ProductCreateDTO;
import by.ezer.dto.ProductDTO;
import by.ezer.dto.RoleCreateDTO;
import by.ezer.dto.RoleDTO;
import by.ezer.entity.Product;
import by.ezer.entity.Role;
import by.ezer.mappers.RoleMapper;
import by.ezer.repositories.api.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
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
        Role role = new Role(request.name());

        roleRepository.save(role);

        return roleMapper.toDto(role);
    }
}
