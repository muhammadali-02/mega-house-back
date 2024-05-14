package project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.dto.RoleDto;
import project.model.Pagination;
import project.model.Permission;
import project.model.Role;
import project.repository.RoleRepository;

import java.util.*;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Map<String, Object> findAll(String search, Pagination pagination) {
        Pageable paging;
        if (pagination.getOrder() == null || pagination.getOrder().equals(""))
            paging = PageRequest.of(pagination.getPage(), pagination.getLimit(), Sort.by("id").descending());
        else {
            if (pagination.getType().equalsIgnoreCase("ASC"))
                paging = PageRequest.of(pagination.getPage(), pagination.getLimit(), Sort.by(pagination.getOrder()).ascending());
            else
                paging = PageRequest.of(pagination.getPage(), pagination.getLimit(), Sort.by(pagination.getOrder()).descending());
        }
        Page<RoleDto> page;
        if (search == null || search.isEmpty()) page = roleRepository.findAllM(paging);
        else page = roleRepository.findAllSearch(search.toLowerCase(), paging);
        Map<String, Object> map = new HashMap<>();
        if (page != null && page.hasContent()) {
            map.put("list", page.getContent());
            map.put("total", page.getTotalElements());
        } else {
            map.put("list", new ArrayList<>());
            map.put("total", 0);
        }
        return map;
    }

    public Role getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            log.warn("IN findById - no role found by id: {}", id);
            return null;
        }
        log.info("IN findById - role: {} found by id: {}", role, id);
        return role;
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
        log.info("IN delete - role with id: {} successfully deleted", id);
    }

    public Role createRole(Role role) {
        Role roleDB = findByCode(role.getCode());
        if (roleDB == null) {
            roleRepository.save(role);
            return role;
        } else {
            log.info("IN create - user with username: {} exists", role.getCode());
            return null;
        }
    }

    public Role findByCode(String code) {
        Role role = roleRepository.findByCode(code);
        if (role == null) {
            log.warn("IN findByCode - no role found by code: {}", code);
            return null;
        }
        log.info("IN findByCode - role: {} found by code: {}", role, code);
        return role;
    }

    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    public List<String> findPermissionNames(List<Long> roleIds) {
        return roleRepository.findPermissionNames(roleIds);
    }

    public Set<Permission> findPermissions(List<Long> roleIds) {
        return roleRepository.findPermissions(roleIds);
    }

    public List<Role> findAllByIds(List<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }
}
