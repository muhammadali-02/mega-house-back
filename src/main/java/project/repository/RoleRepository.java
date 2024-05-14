package project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import project.dto.RoleDto;
import project.model.Permission;
import project.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link Role}.
 */

public interface RoleRepository extends PagingAndSortingRepository<Role, Long>, JpaRepository<Role, Long>, QueryByExampleExecutor<Role> {
    Role findByName(String name);

    Role findByCode(String code);

    @Query("SELECT p.name FROM Role r LEFT JOIN r.permissions p WHERE r.id IN(?1) ORDER BY p.name")
    List<String> findPermissionNames(List<Long> roleIds);

    @Query("SELECT p FROM Role r LEFT JOIN r.permissions p WHERE r.id IN(?1) ORDER BY p.name")
    Set<Permission> findPermissions(List<Long> roleIds);

    @Query("SELECT new project.dto.RoleDto(c) FROM Role c ORDER BY c.id DESC")
    Page<RoleDto> findAllM(Pageable paging);

    @Query("SELECT new project.dto.RoleDto(c) FROM Role c WHERE LOWER(c.name) LIKE %?1% OR LOWER(c.code) LIKE %?1% ORDER BY c.id DESC")
    Page<RoleDto> findAllSearch(String search, Pageable paging);
}
