package project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.exception.RecordNotFoundException;
import project.model.Permission;
import project.repository.PermissionRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public List<Permission> findAllByStaff() {
        List<Permission> listStaffList = permissionRepository.findAllByTypeStartingWithOrderByName("Staff");
        if (listStaffList.size() > 0) {
            log.info("In findAllStaff - {} listStaffList found", listStaffList);
            return listStaffList;
        } else throw new RecordNotFoundException("Not Found StaffTypes in Permission");
    }

    public List<Permission> findAllByEmployee() {
        List<Permission> listEmployeeList = permissionRepository.findAllByTypeStartingWithOrderByName("Employee");
        if (listEmployeeList.size() > 0) {
            log.info("In findAllStaff - {} listEmployeeList found", listEmployeeList);
            return listEmployeeList;
        } else throw new RecordNotFoundException("Not Found EmployeeTypes in Permission");
    }

    public List<Permission> findAllByDirectory() {
        List<Permission> listDirectoryList = permissionRepository.findAllByTypeStartingWithOrTypeStartingWithOrderByName("Directory", "Reference");
        if (listDirectoryList.size() > 0) {
            log.info("In findAllStaff - {} listDirectoryList found", listDirectoryList);
            return listDirectoryList;
        } else throw new RecordNotFoundException("Not Found DirectoryTypes in Permission");
    }

    public List<Permission> findAllByOtherTypes() {
        List<Permission> otherList =
                permissionRepository.findAllByTypeIsNotContainingAndTypeIsNotContainingAndTypeIsNotContainingOrderByName("Employee", "Directory", "Staff");
        if (otherList.size() > 0) {
            log.info("In findAllStaff - {} OtherLists found", otherList);
            return otherList;
        } else throw new RecordNotFoundException("Not Found OtherLists in Permission");
    }

    public List<Permission> findAll() {

        List<Permission> permissionList = (List<Permission>) permissionRepository.findAll();
        if (permissionList.size() > 0) {
            log.info("IN findAll - {} permissions found", permissionList);
            return permissionList;
        } else return null;
    }

    public void savePermission(Permission permission) {
        permissionRepository.save(permission);
        log.info("IN save - permission: {} successfully saved", permission);
    }

    public Permission getByPermissionId(Long id) {
        Permission permission = permissionRepository.findById(id).orElse(null);
        if (permission == null) {
            log.warn("IN findById - no permission found by id: {}", id);
            return null;
        }

        log.info("IN findById - permission: {} found by id: {}", permission, id);
        return permission;
    }

    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
        log.info("IN delete - permission with id: {} successfully deleted", id);
    }

    public Permission getPermissionByName(String name) {
        return permissionRepository.findByName(name);
    }

    public List<Permission> findNotInRole(Long roleId) {
        return permissionRepository.findNotInRole(roleId);
    }

    public List<Permission> findAllByRoleId(Long roleId) {
        return permissionRepository.findAllByRolesId(roleId);
    }

    public List<Permission> getByPermissionIds(List<Long> permissionIds) {
        return permissionRepository.findAllByPermissionIds(permissionIds);
    }
}
