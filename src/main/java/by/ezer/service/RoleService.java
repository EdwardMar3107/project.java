package by.ezer.service;

import by.ezer.dto.RoleCreateDTO;
import by.ezer.dto.RoleDTO;
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

    public RoleDTO findById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Cannot find role by id in service", HttpStatus.BAD_REQUEST));
        return roleMapper.toDto(role);
    }

    public List<RoleDTO> findAll() {
        try {
            return roleMapper.toDto(roleRepository.findAll());
        } catch (Exception e) {
            throw new ServiceException("Cannot find all roles in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public RoleDTO save(RoleCreateDTO roleCreationDto) {
        try {
            Role entity = roleMapper.toEntity(roleCreationDto);
            return roleMapper.toDto(roleRepository.save(entity));
        } catch (Exception e) {
            throw new ServiceException("Cannot save this role in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public RoleDTO update(Long id, RoleCreateDTO roleCreationDto) {
        try {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Cannot find role by id in service", HttpStatus.BAD_REQUEST));
            Role entity = roleMapper.toEntity(roleCreationDto);
            updateRole(role, entity);
            roleRepository.save(role);
            return roleMapper.toDto(role);
        } catch (Exception e) {
            throw new ServiceException("Cannot update this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Cannot delete this user in service", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateRole(Role role, Role source){
        role.setName(source.getName());
        role.setUsers(source.getUsers());
    }

}
