package project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.model.Permission;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
    Permission findByName(String name);

    @Query(value = "SELECT p FROM Permission p WHERE p.id NOT IN (SELECT p.id FROM Permission p LEFT JOIN p.roles r WHERE r.id = ?1)")
    List<Permission> findNotInRole(Long roleId);

    List<Permission> findAllByRolesId(Long roleId);

    List<Permission> findAllByTypeStartingWithOrderByName(String type);

    List<Permission> findAllByTypeStartingWithOrTypeStartingWithOrderByName(String type, String type1);

    List<Permission> findAllByTypeIsNotContainingAndTypeIsNotContainingAndTypeIsNotContainingOrderByName(String eType, String dType, String sType);

    @Query(value = "SELECT p FROM Permission p WHERE p.id IN (?1)")
    List<Permission> findAllByPermissionIds(List<Long> permissionIds);
}
