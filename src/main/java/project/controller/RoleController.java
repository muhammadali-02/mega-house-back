package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.dto.RoleDto;
import project.model.Pagination;
import project.model.Role;
import project.service.PermissionService;
import project.service.RoleService;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v.1/role")
public class RoleController {
    private final RoleService roleService;
    private final PermissionService permissionService;

    @RequestMapping(value = "/list", method = POST)
    @PreAuthorize("hasAuthority('List Roles')")
    public ResponseEntity<Map<String, Object>> listRole(@RequestParam(required = false) String search, @RequestBody Pagination pagination) {
        return new ResponseEntity<>(roleService.findAll(search, pagination), OK);
    }

    @RequestMapping(value = "/add-permissions/{roleId}", method = POST)
    public ResponseEntity<String> listRoleAddPermissionId(@PathVariable("roleId") Long roleId, @RequestBody List<Long> permissionIds) {
        Role role = roleService.getRoleById(roleId);
        role.getPermissions().clear();
        role.getPermissions().addAll(permissionService.getByPermissionIds(permissionIds));
        roleService.updateRole(role);
        return new ResponseEntity<>("Success", OK);
    }


    @RequestMapping(value = "/get-by-id/{id}", method = GET)
    @PreAuthorize("hasAuthority('Get Role')")
    public ResponseEntity<RoleDto> roleById(@PathVariable(name = "id") Long id) {
        Role role = roleService.getRoleById(id);
        if (role == null) return new ResponseEntity("NOT_FOUND", NOT_FOUND);
        return new ResponseEntity<>(new RoleDto(role), OK);
    }

    @RequestMapping(value = "/create", method = POST)
    @PreAuthorize("hasAuthority('Create Role')")
    public ResponseEntity<String> createRole(@RequestBody RoleDto roleDto) {
        Role role = roleDto.convertToRole();
        role.setCode("ROLE_" + role.getName().toUpperCase());
        Role roleDB = roleService.createRole(role);
        if (roleDB == null) return new ResponseEntity<>("Such RoleName exists...", BAD_REQUEST);
        return ResponseEntity.ok("SUCCESSFULLY Saved...");
    }

    @RequestMapping(value = "/update", method = POST)
    @PreAuthorize("hasAuthority('Update Role')")
    public ResponseEntity<String> updateRole(@RequestBody RoleDto roleDto) {
        if (roleDto.getId() == null) return new ResponseEntity<>("Such RoleName exists...", BAD_REQUEST);
        Role role = roleService.getRoleById(roleDto.getId());
        if (role == null) return new ResponseEntity<>("NOT_FOUND", NOT_FOUND);
        roleDto.convertToRole(role);
        role.setCode("ROLE_" + roleDto.getName().toUpperCase());
        Role roleDB = roleService.createRole(role);
        if (roleDB == null) return new ResponseEntity<>("Such RoleName exists...", BAD_REQUEST);
        else return ResponseEntity.ok("SUCCESSFULLY Updated...");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('Delete Role')")
    public ResponseEntity<String> deleteRole(@PathVariable(name = "id") Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("SUCCESSFUL Deleted...");
    }
}
