package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.PermissionDto;
import project.model.Permission;
import project.service.PermissionService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v.1/permission")
public class PermissionController {
    private final PermissionService permissionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Map<String, List<PermissionDto>>>> listPermission() {
//        <!--- Create Map --->
        Map<String, List<PermissionDto>> mapStaff = new LinkedHashMap<>();
        Map<String, List<PermissionDto>> mapEmployee = new LinkedHashMap<>();
        Map<String, List<PermissionDto>> mapDirectory = new LinkedHashMap<>();
        Map<String, List<PermissionDto>> mapOther = new LinkedHashMap<>();

        List<Permission> listStaffList = permissionService.findAllByStaff();
        List<Permission> listEmployeeList = permissionService.findAllByEmployee();
        List<Permission> listDirectoryList = permissionService.findAllByDirectory();
        List<Permission> otherList = permissionService.findAllByOtherTypes();
//        StaffList
        for (Permission permission : listStaffList) {
            String key = permission.getType();
            if (key == null || key.equals("")) {
                key = "Undefined";
            }
            List<PermissionDto> dtos;
            if (mapStaff.containsKey(key)) {
                dtos = mapStaff.get(key);
            } else {
                dtos = new ArrayList<>();
                mapStaff.put(key, dtos);
            }
            dtos.add(new PermissionDto(permission));
        }
//        EmployeeList
        for (Permission permission : listEmployeeList) {
            String key = permission.getType();
            if (key == null || key.equals("")) {
                key = "Undefined";
            }
            List<PermissionDto> dtos;
            if (mapEmployee.containsKey(key)) {
                dtos = mapEmployee.get(key);
            } else {
                dtos = new ArrayList<>();
                mapEmployee.put(key, dtos);
            }
            dtos.add(new PermissionDto(permission));
        }
//        Directory
        for (Permission permission : listDirectoryList) {
            String key = permission.getType();
            if (key == null || key.equals("")) {
                key = "Undefined";
            }
            List<PermissionDto> dtos;
            if (mapDirectory.containsKey(key)) {
                dtos = mapDirectory.get(key);
            } else {
                dtos = new ArrayList<>();
                mapDirectory.put(key, dtos);
            }
            dtos.add(new PermissionDto(permission));
        }
//        OtherType
        for (Permission permission : otherList) {
            String key = permission.getType();
            if (key == null || key.equals("")) {
                key = "Undefined";
            }
            List<PermissionDto> dtos;
            if (mapOther.containsKey(key)) {
                dtos = mapOther.get(key);
            } else {
                dtos = new ArrayList<>();
                mapOther.put(key, dtos);
            }
            dtos.add(new PermissionDto(permission));
        }
        Map<String, Map<String, List<PermissionDto>>> map = new HashMap<>(4);
        map.put("Staff", mapStaff);
        map.put("Employee", mapEmployee);
        map.put("Directory", mapDirectory);
        map.put("Other", mapOther);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/list-by-roleId/{roleId}", method = RequestMethod.GET)
    public ResponseEntity<List<PermissionDto>> listPermissionByRoleId(@PathVariable("roleId") Long roleId) {
        List<Permission> permissionList = permissionService.findAllByRoleId(roleId);
        List<PermissionDto> permissionDto = new ArrayList<>(permissionList.size());
        permissionList.forEach(permission -> permissionDto.add(new PermissionDto(permission)));
        return new ResponseEntity<>(permissionDto, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PermissionDto> permissionById(@PathVariable(name = "id") Long id) {
        Permission permission = permissionService.getByPermissionId(id);
        if (permission == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(new PermissionDto(permission), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPermission(@RequestBody PermissionDto permissionDto) {
        Permission permission = permissionDto.convertToPermission();
        permissionService.savePermission(permission);
        return ResponseEntity.ok("SUCCESSFULLY Saved...");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updatePermission(@RequestBody PermissionDto permissionDto) {
        if (permissionDto.getId() == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Permission permission = permissionService.getByPermissionId(permissionDto.getId());
        if (permission == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        permissionDto.convertToPermission(permission);
        permissionService.savePermission(permission);
        return ResponseEntity.ok("SUCCESSFULLY Updated...");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePermission(@PathVariable(name = "id") Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok("SUCCESSFUL Deleted...");
    }
}
